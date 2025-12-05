import java.io.*;
import java.net.*;

public class WarehouseServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        boolean listening = true;
        String serverName = "WarehouseServer";
        int serverPort = 4545;

        Warehouse warehouse = new Warehouse();

        try {
            serverSocket = new ServerSocket(serverPort);
        } catch (IOException e) {
            System.err.println("Could not start " + serverName + " on port " + serverPort);
            System.exit(-1);
        }

        System.out.println(serverName + " started on port " + serverPort);

        while (listening) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected.");

                new ClientHandler(clientSocket, "ClientHandler", warehouse).start();

            } catch (IOException e) {
                System.err.println("Accept failed on port " + serverPort);
                e.printStackTrace();
            }   
        
        }

        serverSocket.close();

    }
}
