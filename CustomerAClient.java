import java.io.*;
import java.net.*;

public class CustomerAClient {
    public static void main(String[] args) throws IOException {

        Socket customerSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        String serverName = "localhost";
        int serverPort = 4545;

        try {
            customerSocket = new Socket(serverName, serverPort);
            out = new PrintWriter(customerSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(customerSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for connection to port " + serverPort);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;

        System.out.println("Customer A connected to WarehouseServer");

        // Read initial welcome message from server
        fromServer = in.readLine();
        if (fromServer != null) {
            System.out.println("Server: " + fromServer);
        }

        // Main loop: CLIENT SENDS FIRST, then reads server response
        while (true) {
            fromUser = stdIn.readLine();
            if (fromUser != null) {
                System.out.println("CustomerA: " + fromUser);
                out.println(fromUser);
            }

            fromServer = in.readLine();      // wait for server reply
            if (fromServer == null) {
                break;                        // server closed connection
            }

            System.out.println("Server: " + fromServer);

            if (fromServer.equals("Bye.")) {
                break;                        // end session
            }
        }

        out.close();
        in.close();
        customerSocket.close();
    }
}