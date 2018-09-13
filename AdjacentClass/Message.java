package AdjacentClass;

public class Message {
    public String command;
    public String text;
    public String room;

    public Message(String text, String room, String command) {
        this.text = text;
        this.room = room;
        this.command = command;
    }
}
