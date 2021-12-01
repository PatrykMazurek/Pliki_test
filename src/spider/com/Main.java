package spider.com;

import spider.com.chatserver.ChatClient;
import spider.com.database.DBConnection;
import spider.com.fileserver.FileClient;
import spider.com.fileserver.FileServer;
import spider.com.server.Client;
import spider.com.server.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static List<Integer> numberList;

    public static void main(String[] args) {
        // write your code here

//        Client c = new Client(9811, "10.0.50.89"); // łączenie z aplikacją serwerową

//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    ServerSocket serverSocket = new ServerSocket(9811);
//                    while(true){
//                        Socket socket = serverSocket.accept();
//                        FileServer fServer = new FileServer(socket);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        t.start();
//
//        FileClient fc = new FileClient("10.55.0.54", 9811);
//        fc.startConnection();

        DBConnection dbConnection = new DBConnection();

//        Server server = new Server(9811);
//        ChatServer chatServer = new ChatServer(9811);
//        chatServer.startServer();

//        ChatClient chatClient = new ChatClient("10.55.0.41", 9811);


//        Client c = new Client(9811, "10.55.0.41");
//        c.startConnection();
//        c.talkToServer();
//        c.stopConnection();
    }
}
