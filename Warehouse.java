import java.io.*;
public class Warehouse {

    private int apples = 100;
    private int oranges = 100;

    private boolean accessing = false;
    private int threadsWaiting = 0;

    public Warehouse() {

    }

    public synchronized void acquireLock() throws InterruptedException {
        Thread me = Thread.currentThread();
        System.out.println(me.getName() + " is trying to acquire lock ");
        threadsWaiting++;

        while (accessing) {
            System.out.println(me.getName() + " waiting to acquire a lock ");
            wait();
        }

        threadsWaiting--;
        accessing = true;
        System.out.println(me.getName() + " lock acquired ");
    }

    public synchronized void releaseLock() {
        accessing = false;
        notifyAll();

        Thread me = Thread.currentThread();
        System.out.println(me.getName() + " lock released ");
    }

    public synchronized String processCommand(String threadName, String theInput) {
        
        System.out.print(threadName + " recieved: " + theInput);
        String theOutput = null;

        String input = theInput.trim().toUpperCase();

        if (input.startsWith("BUY_APPLES")) {
            try {
                int amount = Integer.parseInt(input.split("")[1]);
                apples -= amount;

                System.out.println(threadName + " bought " + amount + " apples. New total: " + apples);
                theOutput = ("Bought " + amount + " apples. Stock now: apples= " + apples + ", oranges=" + oranges);

            } catch (Exception e) {
                theOutput = "Invalid BUY_APPLES command. Use: BUY_APPLES <number>";
            }
        }

        else if (input.startsWith("BUY_ORANGES")) {
            try {
                int amount = Integer.parseInt(input.split(" ")[1]);
                oranges -= amount;
                
                System.out.println(threadName + " bought " + amount + " oranges. New total: " + oranges);
                theOutput = "Bought " + amount + " oranges. Stock now: apples= " + apples + ", oranges=" + oranges;

            } catch (Exception e) {
                theOutput = "Invalid BUY_ORANGES command. Use: BUY_ORANGES <number>";
            }
        }

        else if (input.startsWith("ADD_APPLES")) {
            try {
                int amount = Integer.parseInt(input.split(" ")[1]);
                apples += amount;

                System.out.println(threadName + " added " + amount + " apples. New total: " + apples);
                theOutput = "Added " + amount + " apples. Stock now: apples=" + apples + ", oranges=" + oranges;
            } catch (Exception e) {
                theOutput = "Invalid ADD_APPLES command. Use: ADD_APPLES <number>";
            }
        }
            
        else if (input.startsWith("ADD_ORANGES")){
            try {
                int amount = Integer.parseInt(input.split(" ")[1]);
                oranges += amount;

                System.out.println(threadName + "added " + amount + "oranges. New total: " + oranges);
                theOutput = "Added " + amount + " oranges. Stock now: apples=" + apples + ", oranges=" + oranges;
            } catch (Exception e) {
                theOutput = "Invalid ADD_ORANGES command. Use: ADD_ORANGES <number>";
            }
            
        }

        else if (input.equals("CHECK_STOCK")) {
            theOutput = "Stock: apples=" + apples + ", oranges=" + oranges;
        }

        else if (input.equals("QUIT") || input.equals("EXIT")) {
            theOutput = "Bye.";
        }

        else {
            theOutput = "Unknown command. Try: BUY_APPLES (X), BUY_ORANGES (X), ADD_APPLES (X), ADD_ORANGES (X), CHECK_STOCK, QUIT";
        }
        
        return theOutput;
    }
    
}
