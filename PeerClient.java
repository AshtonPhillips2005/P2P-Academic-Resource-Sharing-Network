import java.io.*;
import java.net.*;

public class PeerClient {

    public void requestFile(String ip, int port, String fileName) {

        try (
            Socket socket = new Socket(ip, port);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        ) {

            out.writeUTF("GET " + fileName);

            String status = in.readUTF();

            if (status.equals("ERROR")) {
                System.out.println("[CLIENT] File not found on peer.");
                return;
            }

            if (!status.equals("OK")) {
                System.out.println("[CLIENT] Unexpected server response.");
                return;
            }

            FileOutputStream fos = new FileOutputStream("downloaded_" + fileName);

            while (true) {

                int chunkId = in.readInt();

                // END OF FILE SIGNAL
                if (chunkId == -1) {
                    in.readUTF(); // consume "END"
                    break;
                }

                int size = in.readInt();

                byte[] data = new byte[size];
                in.readFully(data);

                String receivedHash = in.readUTF();
                String calculatedHash = HashUtil.sha256(data);

                if (!receivedHash.equals(calculatedHash)) {
                    System.out.println("[CLIENT] Corrupted chunk " + chunkId);
                    out.writeUTF("NACK");
                    continue;
                }

                fos.write(data);
                out.writeUTF("ACK");

                System.out.println("[CLIENT] Received chunk " + chunkId + " | writing to file...");
            }

            fos.close();
            socket.close();

            System.out.println("Download complete -> downloaded_" + fileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}