package spider.com.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ChatServer{

    private Set<String> userList;
    private Set<ChatUserHandler> chatUserList;
    private int port;
    private ServerSocket serverSocket;

    public ChatServer(int port) {
        userList = new HashSet<>();
        chatUserList = new HashSet<>();
        this.port = port;

    }

    public void startServer(){
        try {
            serverSocket = new ServerSocket(this.port);
            System.out.println("Oczekuje na połącznie...");
            while (true){
                Socket chatSocket = serverSocket.accept();
                ChatUserHandler chatUser = new ChatUserHandler(chatSocket, this);
                chatUserList.add(chatUser);
                chatUser.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopServer(){
        if(!serverSocket.isClosed()){
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addUser(String user){
        userList.add(user);
    }

    public void removeUser(String user, ChatUserHandler chat){
        boolean removeUser = userList.remove(user);
        if (removeUser){
            chatUserList.remove(chat);
            System.out.println("Użytkownik " + user + " opuścił czat" );
        }
    }

    public void broadcastUser(String message){
        for (ChatUserHandler chat : chatUserList){
            chat.sendMessage(message);
        }
    }



}
