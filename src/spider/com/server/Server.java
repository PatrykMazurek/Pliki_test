package spider.com.server;

// 9811
// 9812

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private Socket socket;
    private ServerSocket serverSocket;

    public Server(int port){
        try {
            serverSocket = new ServerSocket(port);
            while (true){
                System.out.println("Oczekuje na połączenie...");
//                socket = serverSocket.accept();
                new ClientHandler(serverSocket.accept()).start();
                System.out.println("połączono z użytkownikiem ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
