import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
  public static void main(String[] args){
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.out.println("Logs from your program will appear here!");

    int port = 6379;
    for (int i = 0; i < args.length - 1; i++) {
      if (args[i].equals("--port")) {
        port = Integer.parseInt(args[i + 1]);
      }
    }

    try {
      ServerSocket serverSocket = new ServerSocket(port);
      serverSocket.setReuseAddress(true);
      while (true) {
        Socket clientSocket = serverSocket.accept();
        // Each client gets its own virtual thread so the accept loop above
        // never blocks waiting on one client's traffic.
        //This spins up a virtual thread per client - cheap
        //Using Platform threads is expensive
        //new Thread(() -> handleClient(clientSocket)).start();  Platform Thread
        Thread.ofVirtual().start(() -> handleClient(clientSocket));
      }
    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
    }
  }

  private static void handleClient(Socket clientSocket) {
    try (clientSocket) {
      InputStream inputStream = clientSocket.getInputStream();
      byte[] buffer = new byte[1024];
      int bytesRead;
      while ((bytesRead = inputStream.read(buffer)) != -1) {
        clientSocket.getOutputStream().write("+PONG\r\n".getBytes());
      }
    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
    }
  }
}
