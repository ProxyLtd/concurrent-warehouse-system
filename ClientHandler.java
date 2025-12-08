import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {
    private Socket clientSocket = null;
    private Warehouse warehouse;
    private String threadName;

    public ClientHandler(Socket socket, String threadName, Warehouse warehouse) {
        super(threadName);
        this.clientSocket = socket;
        this.threadName = threadName;
        this.warehouse = warehouse;
    
    }

    public void run() {
        System.out.println(threadName + " initialising.");

        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine = null;

            System.out.println(threadName + " sending welcome message.");
            out.println("Welcome to the Warehouse.");
            out.flush();
            
            while ((inputLine = in.readLine()) != null) {

                try {
                    warehouse.acquireLock();
                    outputLine = warehouse.processCommand(threadName, inputLine);
                    out.println(outputLine);
                    warehouse.releaseLock();
                } catch (InterruptedException e) {
                    System.err.println(threadName + " failed to acquire lock.");
                }

                if (outputLine.equals("Bye.")) {
                    break;
                }
            }

            out.close();
            in.close();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(threadName + " terminating");
    }
    

}
