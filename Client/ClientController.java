package Client;

import AdjacentClass.Transformer;
import AdjacentClass.Transformer_JSON;
import AdjacentClass.Notification;
import javax.swing.*;

public class ClientController {
    Connector connector;
    Client client;
    ClientModel clientModel;

    public ClientController(Connector connector, Client client, ClientModel clientModel) {
        this.connector = connector;
        this.client = client;
        this.clientModel = clientModel;
    }

    public void handleServerNotification(String str) {
        Transformer transformer = Transformer_JSON.getInstance();
        Notification notification = transformer.getObject(str, Notification.class);
        switch (notification.command){
            case "updateMessages" : updateMessages(notification); break;
            case "newAvailiableChat" : newAvailiableChat(notification); break;
            case "login" : login(notification); break;
            case "register" : register(notification); break;
            case "userIsExist" : JOptionPane.showMessageDialog(null, "user is exist"); break;
            case "unLogin" : client.setAuthPanel(); break;
            case "chatIsExist" : JOptionPane.showMessageDialog(null, "chat is exist"); break;
            case "userNotFound" : JOptionPane.showMessageDialog(null, "user not found"); break;
        }
    }

    private void newAvailiableChat(Notification notification) {
        if(!clientModel.availiableChats.contains(notification.text)) {
            clientModel.availiableChats.add(notification.text);
            clientModel.fireTableDataChanged();
        }
    }

    private void updateMessages(Notification notification){
        clientModel.checkForNewMessages(notification.messages);
        clientModel.setMessagesMap(notification.messages);
        client.setMessages(notification.messages.get(clientModel.activeChat));
    }

    private void login(Notification notification){
        String[] res = notification.text.split(",");
        if(res[0].equals("ok")){
            reglog(notification, res);
            clientModel.fireTableDataChanged();
            clientModel.activeChat = res[2];
            updateMessages(notification);
        }else
            JOptionPane.showMessageDialog(null, "error");
    }

    private void register(Notification notification) {
        String[] res = notification.text.split(",");
        reglog(notification, res);
        clientModel.fireTableDataChanged();
        clientModel.activeChat = res[2];
        updateMessages(notification);
    }

    private void reglog(Notification notification, String[] res) {
        if(res[1].toLowerCase().trim().equals("admin")){
            notification.messages.forEach((k,v) -> clientModel.availiableChats.add(k));
            client.setAdminPanel();
        }else {
            clientModel.availiableChats.add(res[2]);
            client.setUserPanel(res[1]);
        }
    }
}
