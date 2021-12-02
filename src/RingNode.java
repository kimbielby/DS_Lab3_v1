import java.net.*;

public class RingNode {
    private ServerSocket s;

    public RingNode(int this_port, String next_host, int next_port){
        
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
