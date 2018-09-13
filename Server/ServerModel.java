package Server;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerModel {
    HashMap<String, Room> rooms = new HashMap<String, Room>();
    CopyOnWriteArrayList<Connectionable> connections = new CopyOnWriteArrayList<Connectionable>();

    public void createRoom(String name){
        Room room = new Room(name);
        rooms.put(name, room);
    }

    public void ban(String login) {
        for(Connectionable connection : connections)
            if (connection.getLogin().equals(login))
                connection.setBan(true);
    }

    public void deban(String login) {
        for(Connectionable connection : connections)
            if (connection.getLogin().equals(login))
                connection.setBan(false);
    }

    public void delete(String login) {
        for(Connectionable connection : connections)
            if (connection.getLogin().equals(login)) {
                connections.remove(connection);
                connection.close();
            }
    }
}
