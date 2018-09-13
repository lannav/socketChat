package Server;

import AdjacentClass.Notification;
import AdjacentClass.Transformer;
import AdjacentClass.Transformer_JSON;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class DesktopConnection implements Connectionable {
    private String login = "";
    private String password = "";
    private boolean isAuthorized = false;
    private boolean isBan = false;
    private boolean isAdmin = false;

    private Socket socket;
    private Scanner inMessage;
    private PrintWriter outMessage;
    private ServerController serverController;

    public DesktopConnection(Socket socket, ServerController serverController) {
        this.serverController = serverController;
        try {
            this.socket = socket;
            this.outMessage = new PrintWriter(socket.getOutputStream(),true);
            this.inMessage = new Scanner(socket.getInputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (inMessage.hasNext()) {
                    String inMes = inMessage.nextLine();
//                    inMes = inMes.substring(0, inMes.length() - 1);
                    serverController.handleClientMessage(inMes, this);
                }
                Thread.sleep(100);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    public void sendNotification(Notification notification){
        Transformer transformer = Transformer_JSON.getInstance();
        outMessage.println(transformer.getString(notification));
    }
    public void close(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isBan() {
        return isBan;
    }

    @Override
    public boolean isAdmin() {
        return isAdmin;
    }

    @Override
    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public boolean isAuthorized() {
        return isAuthorized;
    }

    @Override
    public void setAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void setBan(boolean isBan) {
        this.isBan = isBan;
    }
}
