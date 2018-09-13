package AdjacentClass;

import java.util.HashMap;

public class Notification {
    public String command;
    public HashMap<String, String> messages;
    public String text = "";

    public Notification(String command, HashMap<String, String> messages) {
        this.command = command;
        this.messages = messages;
    }
}
