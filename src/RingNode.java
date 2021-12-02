import java.net.*;

public class RingNode {
    private ServerSocket servSock;
    private Socket thisNodeSock;
    private Socket nextNodeSock;

    public RingNode(int thisPort, String nextHost, int nextPort){

        // Create the server socket with the current port
        try {
            servSock = new ServerSocket(thisPort);
            System.out.println("Server socket on ring node is listening ....");
        }
        catch (Exception e){}

        // Listen for connections and create them
        while (true) {
            try {
                // Accept the server socket and the token
                thisNodeSock = servSock.accept();

                // Confirm that token has been received
                System.out.println("Server socket has received the token");

                // DO SECTION TO UPDATE THE FILE

                // Close the current socket
                thisNodeSock.close();

                // Create a new socket to send the token to
                nextNodeSock = new Socket(nextHost, nextPort);

                // Check that the connection was successful
                if (nextNodeSock.isConnected()){
                    // Do a print line
                }
                else{
                    // Do a print line
                }

                // DO A TRY-CATCH THREAD SLEEP FOR A WEE BIT

                // Close the new socket
                nextNodeSock.close();

                // DO A TRY-CATCH THREAD SLEEP FOR A WEE BIT

                //Check that the connection was successfully closed
                if (nextNodeSock.isClosed()){
                    // Do a print line
                }
                else {
                    // Do a print line
                }
            }
            catch (Exception e){}
        }
    }

    public static void main(String args[]){
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
