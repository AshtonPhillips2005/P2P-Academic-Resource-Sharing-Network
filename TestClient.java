public class TestClient {

    public static void main(String[] args) {

        PeerClient client = new PeerClient();

        int targetPort = Integer.parseInt(args[0]);

        client.requestFile("127.0.0.1", targetPort, "test.txt");
    }
}