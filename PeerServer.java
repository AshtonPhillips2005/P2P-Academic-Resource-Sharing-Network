import java.net.ServerSocket;
import java.net.Socket;

public class PeerServer implements Runnable {
   private int port;

   public PeerServer(int var1) {
      this.port = var1;
   }

   public void run() {
      try {
         ServerSocket var1 = new ServerSocket(this.port);

         try {
            System.out.println("Peer Server running on port " + this.port);

            while(true) {
               Socket var2 = var1.accept();
               System.out.println("Connection received from peer");
               (new Thread(new ClientHandler(var2))).start();
            }
         } catch (Throwable var5) {
            try {
               var1.close();
            } catch (Throwable var4) {
               var5.addSuppressed(var4);
            }

            throw var5;
         }
      } catch (Exception var6) {
         var6.printStackTrace();
      }
   }
}
