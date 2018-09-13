package Client;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;

public class Listeners implements WindowListener, ActionListener, ListSelectionListener {

    private Connector connector;
    private ClientModel clientModel;
    private Client client;

    public Listeners(Connector connector, ClientModel clientModel, Client client) {
        this.connector = connector;
        this.clientModel = clientModel;
        this.client = client;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        connector.sendToServer("", "disconnect");
       connector.closeConnection();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String message = "";
        switch (e.getActionCommand()){
            case "send" : message = client.getMessageText();
                if (!message.equals("")) {
                    connector.sendToServer(client.getMessageText(), "sendMessage");
                    client.setMessageText("");
                }
                break;
            case "login" : message = client.getDataRegLog();
                if (!message.equals(""))
                    connector.sendToServer(message, "login");
                break;
            case "register" : message = client.getDataRegLog();
                if (!message.equals(""))
                    connector.sendToServer(client.getDataRegLog(), "register");
                break;
            case "ban" : message = client.getSelectedUser();
                if (!message.equals(""))
                    connector.sendToServer(message, "ban");
                break;
            case "deban" : message = client.getSelectedUser();
                if (!message.equals(""))
                    connector.sendToServer(message, "deban");
                break;
            case "delete" : message = client.getSelectedUser();
                if (!message.equals(""))
                    connector.sendToServer(message, "delete");
                break;
            case "addUser" : message = client.getAddFieldText() + "," + clientModel.activeChat;
                if (!message.equals(""))
                    connector.sendToServer(message, "addUserToChat");
                break;
            case "addChat" : message = client.getAddFieldText();
                if (!message.equals(""))
                    connector.sendToServer(message, "addChat");
                break;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (client.tb.getSelectedRow() > -1) {
            String selectedChat = client.tb.getValueAt(client.tb.getSelectedRow(), 0).toString();

            if(selectedChat.contains("---NEW messages")) {
                int index = clientModel.availiableChats.indexOf(selectedChat);
                String temp = clientModel.availiableChats.get(index).replaceAll("---NEW messages", "");
                clientModel.availiableChats.remove(index);
                selectedChat = selectedChat.replaceAll("---NEW messages", "");
                clientModel.availiableChats.add(index,selectedChat);
                clientModel.fireTableDataChanged();
            }

            String messages = clientModel.getChatHistory(selectedChat);
            client.setMessages(messages);
            clientModel.activeChat = selectedChat;
        }
    }
}
