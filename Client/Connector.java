package Client;

import AdjacentClass.Message;
import AdjacentClass.Transformer;
import AdjacentClass.Transformer_JSON;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Connector {
    private Socket socket;
    private Scanner inMessage;
    private PrintWriter outMessage;
    private ClientModel clientModel;

    public Connector(String server, int port, Client client, ClientModel clientModel) {
        this.clientModel = clientModel;
        try {
            socket = new Socket(server, port);
            Socket s = new Socket();
            inMessage = new Scanner(socket.getInputStream()); // datainputstream
            outMessage = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ClientController clientController = new ClientController(this, client, clientModel);
        new Thread(() -> {
            try {
                while (true) {
                    if (inMessage.hasNext()) {
                        String inMes = inMessage.nextLine();
//                        inMes = inMes.substring(0, inMes.length() - 1);
                        clientController.handleServerNotification(inMes);
                    }
                }
            } catch (Exception e) {
            }
        }).start();
    }

    public void sendToServer(String str, String command){
        Message message = new Message(str, clientModel.getActiveChat(), command);
        Transformer transformer = Transformer_JSON.getInstance();
        outMessage.println(transformer.getString(message));
    }

    public void closeConnection(){
        try {
            outMessage.close();
            inMessage.close();
            socket.close();
        } catch (IOException exc) {

        }
    }
}
