import java.io.*;
import java.net.*;
import java.util.Date;

public class RingNode {
    private ServerSocket servSock;
    private Socket thisNodeSock;
    private Socket nextNodeSock;
    private String hostName;
    private RandomAccessFile record;

    public RingNode(int thisPort, String nextHost, int nextPort){

        // Create the server socket with the current port
        try {
            servSock = new ServerSocket(thisPort);

            // Get the host name
            InetAddress hostAddr = InetAddress.getLocalHost();
            hostName = hostAddr.getHostName();

            // Print status
            System.out.println("Ring member hostname is "+hostName);
            System.out.println("Ring member port is "+thisPort);
            System.out.println("Server socket ("+hostName+":  "+thisPort +") on ring node is listening ....");
        }
        catch (Exception e){}

        // Listen for connections and create them
        while (true) {
            try {
                // Accept the server socket and the token
                thisNodeSock = servSock.accept();

                // Confirm that token has been received
                System.out.println("Server socket ("+hostName+":  "+thisPort +") has received the token");
                System.out.println("Ring node has the token");
            }
            catch (Exception e){}

            // Critical section that writes to a file
            try {
                // Create file
                record = new RandomAccessFile("record.txt", "rw");

                // Create the timestamp
                Date tStamp = new Date();

                // Convert the timestamp to a String
                String timeStamp = tStamp.toString();

                // Write the timestamp in the file
                record.writeChars(timeStamp);

                // Print confirmation
                System.out.println("Record from ring node on host "+hostName+", port number "+thisPort+", is "+timeStamp);

                // Close the random access filestream
                record.close();
            }
            catch (IOException e){
                System.out.println("Error writing to file: "+e);
            }

            // Have a pause before starting next part
            try {
                Thread.sleep(3000);
            }
            catch (InterruptedException e){
                System.out.println("Sleep failed: "+e);
            }

            // Close the current socket
            try {
                thisNodeSock.close();

                // Confirm that socket is closed
                System.out.println("Socket from previous ring node is now closed.");
            }
            catch (Exception e){}

            // Create a new socket to send the token to
            try {
                nextNodeSock = new Socket(nextHost, nextPort);

                // Confirm status
                System.out.println("Ring node is now releasing the token");
            }
            catch (Exception e){}

            // Check that the connection was successful
            try {
                if (nextNodeSock.isConnected()){
                    // Confirm that connection was successful
                    System.out.println("Socket to next ring node ("+hostName+":   "+nextPort+") connected okay");
                }
                else{
                    // Advise connection failed
                    System.out.println("** Socket to next ring node ("+hostName+":   "+nextPort+") failed to connect");
                }
            }
            catch (Exception e){}

            // Have a pause before closing the new socket
            try {
                Thread.sleep(100);
            }
            catch (InterruptedException e){
                System.out.println("sleep fail: "+e);
            }

            // Close the new socket (pass token)
            try {
                nextNodeSock.close();

                // Confirm token has been passed
                // System.out.println("Socket to next ring node ("+nextHost+":  "+nextPort+") is now closed");
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
                if (nextNodeSock.isClosed()){
                    System.out.println("Socket to next ring node ("+nextHost+":  "+nextPort+") is now closed");
                    System.out.println("Ring node has released the token");
                }
                else {
                    System.out.println("** Socket to next ring node ("+nextHost+":  "+nextPort+") is still open!!");
                }
            }
            catch (Exception e){}
        }
    }

    public static void main(String[] args){
        if (args.length != 3){
            System.out.println("Usage: [this port] [next host] [next port]");
            System.out.println("Only: "+args.length+" parameters entered");
            System.exit(1);
        }

        int thisPort = Integer.parseInt(args[0]);
        String nextHost = args[1];
        int nextPort = Integer.parseInt(args[2]);

        RingNode ringNode = new RingNode(thisPort, nextHost, nextPort);
    }
}
