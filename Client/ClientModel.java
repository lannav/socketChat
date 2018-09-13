package Client;

import javax.swing.table.AbstractTableModel;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientModel extends AbstractTableModel {
    CopyOnWriteArrayList<String> availiableChats = new CopyOnWriteArrayList<>();
    String activeChat = "noAuth";
    HashMap<String,String> map;

    public ClientModel() {
    }

    @Override
    public int getRowCount() {
        return availiableChats.size();
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return availiableChats.get(rowIndex);
    }

    @Override
    public String getColumnName(int col)
    {
        String[] str = {"Available chats"};
        return str[col];
    }

    public String getActiveChat() {
        return activeChat;
    }

    public void setMessagesMap(HashMap<String,String> map) {
        this.map = map;
    }

    public String getChatHistory(String chatName) {
        return map.get(chatName);
    }

    public void checkForNewMessages(HashMap<String,String> messages) {
        if(map != null){
            map.forEach((k,v) -> {
                if(!k.equals(activeChat) && !messages.get(k).equals(v)) {
                    String temp = availiableChats.get(availiableChats.indexOf(k)).concat("---NEW messages");
                    int index = availiableChats.indexOf(k);
                    availiableChats.remove(availiableChats.get(index));
                    availiableChats.add(index ,temp);
                }
            });
        }
        fireTableDataChanged();
    }
}
