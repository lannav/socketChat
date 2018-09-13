package Server;

import java.util.concurrent.CopyOnWriteArrayList;

public class Room {
    String name;
    String history = "";
    CopyOnWriteArrayList<Connectionable> subscribers = new CopyOnWriteArrayList<Connectionable>();

    public Room(String name) {
        this.name = name;
    }

    public void appendMessage(String msg){
        history += "\n" + msg;
    }
}
