package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Scanner;

public class TestServer {
    public static void main(String[] args) {
        new TestServer();
    }
    static final int PORT = 60000;

    public TestServer() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        Scanner inMessage = null;
        try {
            serverSocket = new ServerSocket(PORT);
            socket = serverSocket.accept();
            while (true) {

                PrintWriter outMessage = new PrintWriter(socket.getOutputStream(),true);
                inMessage =  new Scanner(socket.getInputStream());
                if(inMessage.hasNext())
                    System.out.println(inMessage.nextLine());

                outMessage.println("TEST");
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                socket.close();
                serverSocket.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
