import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {

    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try (
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        ) {

            String request = in.readUTF();

            if (!request.startsWith("GET")) {
                return;
            }

            String fileName = request.split(" ")[1];
            File file = new File(fileName);

            if (!file.exists()) {
                out.writeUTF("ERROR");
                out.flush();
                return;
            }

            out.writeUTF("OK");
            out.flush();

            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[4096];

            int bytes;
            int chunkId = 0;

            while ((bytes = fis.read(buffer)) != -1) {

                byte[] chunkData = new byte[bytes];
                System.arraycopy(buffer, 0, chunkData, 0, bytes);

                String hash = HashUtil.sha256(chunkData);

                boolean sent = false;

                while (!sent) {

                    out.writeInt(chunkId);
                    out.writeInt(bytes);
                    out.write(chunkData);
                    out.writeUTF(hash);
                    out.flush();

                    String response = in.readUTF();

                    if (response.equals("ACK")) {
                        System.out.println("[SERVER] Sent chunk " + chunkId);
                        sent = true;
                        chunkId++;
                    } else {
                        System.out.println("[SERVER] Resending chunk " + chunkId);
                    }
                }
            }

            // END SIGNAL
            out.writeInt(-1);
            out.writeUTF("END");
            out.flush();

            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}