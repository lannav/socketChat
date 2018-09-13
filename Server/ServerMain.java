package Server;

import javax.swing.*;
import java.io.IOException;

public class ServerMain {
    public static void main(String[] args) throws Exception {
        ServerModel serverModel = new ServerModel();
        serverModel.createRoom("chat1");
        ServerController serverController = new ServerController(serverModel);
        new Thread(new WebServer(60001, serverController)).start();
        new DesktopServer(60000, serverController);
    }
}
