package Server;

import web.WebsocketClientEndpoint;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

public class DesktopServer {
    public DesktopServer(int port, ServerController serverController) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            DesktopConnection connection = new DesktopConnection(clientSocket, serverController);
            new Thread(connection).start();
            serverController.addUser(connection);
        }
    }
}
