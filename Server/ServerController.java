package Server;

import AdjacentClass.Message;
import AdjacentClass.Notification;
import AdjacentClass.Transformer;
import AdjacentClass.Transformer_JSON;
import java.util.HashMap;

public class ServerController {
    private ServerModel serverModel;

    public ServerController(ServerModel serverModel) {
        this.serverModel = serverModel;
    }

    public void handleClientMessage(String str, Connectionable connection){
        if(connection.isBan())
            return;

        Transformer transformer = Transformer_JSON.getInstance();
        Message message = transformer.getObject(str, Message.class);
        switch (message.command) {
            case "sendMessage":
                sendMessage(message, connection);
                break;
            case "login":
                login(message.text, connection);
                break;
            case "register":
                register(message.text, connection);
                break;
            case "addUserToChat":
                addUserToChat(message, connection);
                break;
            case "addChat":
                addChat(message, connection);
                break;
            case "disconnect":
                disconnect(connection);
                break;
        }
        if (connection.isAdmin())
            switch (message.command) {
                case "ban":
                    serverModel.ban(message.text);
                    break;
                case "deban":
                    serverModel.deban(message.text);
                    break;
                case "delete":
                    serverModel.delete(message.text);
                    break;
            }
    }

    private void disconnect(Connectionable connection) {
        connection.setAuthorized(false);
    }

    private void addUserToChat(Message message, Connectionable connection) {
        String[] res = message.text.split(",");
        Connectionable us = null;
        for(Connectionable connection1 : serverModel.connections)
            if(connection1.getLogin().equals(res[0]))
                us = connection1;

        if(us != null) {
            serverModel.rooms.get(res[1]).subscribers.add(us);
            HashMap<String, String> map = new HashMap<>();
            serverModel.rooms.forEach((k,v) -> map.put(v.name, v.history));
            Notification notification = new Notification("newAvailiableChat", map);
            notification.text = res[1];
            us.sendNotification(notification);
        }else{
            Notification notification = new Notification("userNotFound", new HashMap<>());
            connection.sendNotification(notification);
        }
    }

    private void addChat(Message message, Connectionable connection) {
        Room room = serverModel.rooms.get(message.text);

        if(room == null){
            serverModel.createRoom(message.text);
            HashMap<String, String> map = new HashMap<>();
            serverModel.rooms.forEach((k,v) -> map.put(v.name, v.history));
            Notification notification = new Notification("newAvailiableChat", map);
            notification.text = message.text;
            connection.sendNotification(notification);
        }else{
            Notification notification = new Notification("chatIsExist", new HashMap<>());
            connection.sendNotification(notification);
        }
    }

    private void register(String text, Connectionable connection) {
        String[] namePass = text.split(",");
        for (Connectionable us : serverModel.connections)
            if (connection != us && namePass[0].equals(us.getLogin())) {
                Notification notification = new Notification("userIsExist", new HashMap<>());
                connection.sendNotification(notification);
                return;
            }

        boolean reg = Auth.getInstance().register(namePass[0], namePass[1]);
        if(!reg){
            Notification notification = new Notification("userIsExist", new HashMap<>());
            connection.sendNotification(notification);
            return;
        }

        if (namePass[0].trim().toLowerCase().equals("admin"))
            connection.setAdmin(true);

        connection.setAuthorized(true);
        connection.setLogin(namePass[0]);
        connection.setPassword(namePass[1]);
        HashMap<String, String> map = new HashMap<>();
        serverModel.rooms.forEach((k, v) -> map.put(v.name, v.history));
        Notification notification = new Notification("register", map);
        notification.text = "ok," + namePass[0] + ",chat1";
        connection.sendNotification(notification);
    }

    private void login(String text, Connectionable connection) {
        String[] namePass = text.split(",");
        for(Connectionable con : serverModel.connections){
            if(namePass[0].equals(con.getLogin()) && namePass[1].equals(con.getPassword())){
                connection.setAdmin(con.isAdmin());
                con = connection;
                con.setAuthorized(true);
                con.setLogin(namePass[0]);
                con.setPassword(namePass[1]);
                HashMap<String, String> map = new HashMap<>();
                serverModel.rooms.forEach((k,v) -> map.put(v.name, v.history));
                Notification notification = new Notification("login", map);
                notification.text = "ok," + namePass[0] + ",chat1";
                con.sendNotification(notification);
                return;
            }
        }

        boolean log = Auth.getInstance().login(namePass[0], namePass[1]);
        if(log){
            connection.setAuthorized(true);
            connection.setLogin(namePass[0]);
            connection.setPassword(namePass[1]);
            if(namePass[0].toLowerCase().trim().equals("admin"))
                connection.setAdmin(true);

            HashMap<String, String> map = new HashMap<>();
            serverModel.rooms.forEach((k,v) -> map.put(v.name, v.history));
            Notification notification = new Notification("login", map);
            notification.text = "ok," + namePass[0] + ",chat1";
            connection.sendNotification(notification);
            return;
        }

        Notification notification = new Notification("login", new HashMap<>());
        notification.text = "no";
        connection.sendNotification(notification);
    }

    private void Logout(Connectionable connection) {
        Notification notification = new Notification("logout", new HashMap<>());
        connection.sendNotification(notification);
    }

    private void sendMessage(Message message, Connectionable connection) {
        if(connection.isAuthorized()) {
            serverModel.rooms.get(message.room).appendMessage(message.text);
            sendNotificationToAllUsers();
        }else{
            Notification notification = new Notification("notAuthorized", new HashMap<>());
            connection.sendNotification(notification);
        }

    }

    private void sendNotificationToAllUsers() {
        HashMap<String, String> map = new HashMap<>();
        serverModel.rooms.forEach((k,v) -> map.put(v.name, v.history));
        Notification notification = new Notification("updateMessages", map);
        for(Connectionable connection : serverModel.connections){
            connection.sendNotification(notification);
        }
    }

    public void addUser(Connectionable user){
        serverModel.connections.add(user);
    }
}
