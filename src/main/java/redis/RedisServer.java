package redis;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class RedisServer {
    private static final int DEFAULT_PORT = 6379;

    public static void main(String[] args) {
        int port = getPort(args);
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Redis server started on port " + port);
            clientSocket = serverSocket.accept();
            System.out.println("Client connected!");
            RespParser parser =
                    new RespParser(clientSocket.getInputStream());
            String line = parser.readBulkString();
            System.out.println("Received: [" + line + "]");
        } catch (IOException e){
            System.out.println("Server Error: " + e.getMessage());
        } finally {
            try{
                if(clientSocket != null){
                    clientSocket.close();
                }
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                System.out.println("Error while closing resources: " + e.getMessage());
            }
        }
    }

    private static int getPort(String[] args){
        if(args.length == 0){
            return DEFAULT_PORT;
        }
        return Integer.parseInt(args[0]);
    }

}
