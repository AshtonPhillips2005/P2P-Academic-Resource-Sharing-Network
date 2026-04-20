public class Main {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Usage: java Main <port>");
            return;
        }

        int port = Integer.parseInt(args[0]);

        new Thread(new PeerServer(port)).start();

        System.out.println("Peer running on port " + port);

        // IMPORTANT: DO NOT auto-connect anymore
        System.out.println("Server ready. You can now manually trigger downloads.");
    }
}