import java.net.*;
import java.io.*;

public class StarterNode {

    public StarterNode(String firstIP, int firstPort){
        
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
