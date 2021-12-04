import java.net.*;
import java.io.*;

public class StarterNode {

    private String thisHost;
    private Socket firstNodeSock;

    public StarterNode(String firstIP, int firstPort){
        // Print status
        System.out.println("Clearing/Creating record.txt file");

        // Clear/Create the file used by the ring nodes
        try {
            // Create file
            RandomAccessFile record = new RandomAccessFile("record.txt", "rw");

            // Close the random access filestream
            record.close();
        }
        catch (IOException e){
            System.err.println("Exception in clearing/creating file:  main:  "+e);
        }

        // Get the host name
        try {
            InetAddress hostAddr = InetAddress.getLocalHost();
            thisHost = hostAddr.getHostName();
        }
        catch (Exception e){}

        // Confirm Starter Node's host name
        System.out.println("Manager hostname is "+thisHost);

        // Confirm first node's IP/host name
        System.out.println("Ring element hostname is "+firstIP);

        // Confirm first node's port number
        System.out.println("Ring element port is "+firstPort);

        // Create a new socket to send the token to
        try {
            firstNodeSock = new Socket(firstIP, firstPort);

            // Confirm status
            System.out.println("Starter node is now injecting the token into the ring");
        }
        catch (Exception e){}

        // Check that the connection was successful
        try {
            if (firstNodeSock.isConnected()){
                // Confirm that connection was successful
                System.out.println("Socket to first ring node ("+firstIP+":   "+firstPort+") connected okay");
            }
            else{
                // Advise connection failed
                System.out.println("** Socket to first ring node ("+firstIP+":   "+firstPort+") failed to connect");
            }
        }
        catch (Exception e){}

        // Have a pause before closing the new socket
        try {
            Thread.sleep(100);
        }
        catch (Exception e){}

        // Close the new socket (pass token)
        try {
            firstNodeSock.close();
        }
        catch (Exception e){}

        // Have another pause
        try {
            Thread.sleep(100);
        }
        catch (InterruptedException e){
            System.out.println("sleep fail: "+e);
        }

        //Check that the connection was successfully closed
        try {
            if (firstNodeSock.isClosed()){
                System.out.println("Socket to first ring node ("+firstIP+":  "+firstPort+") is now closed");
                System.out.println("Starter node has injected the token successfully");
            }
            else {
                System.out.println("** Socket to first ring node ("+firstIP+":  "+firstPort+") is still open!!");
            }
        }
        catch (Exception e){}
    }

    public static void main (String[] args){
        // Make sure correct number of params entered
        if (args.length != 2){
            System.out.println("Usage: [first IP] [first port]");
            System.out.println("Only: "+args.length+" parameters entered");
            System.exit(1);
        }
        // Params to be received
        String firstIP = args[0];
        int firstPort = Integer.parseInt(args[1]);

        // Constructor
        StarterNode starterNode = new StarterNode(firstIP, firstPort);
    }
}
