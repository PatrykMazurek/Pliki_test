package spider.com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatUserHandler extends Thread {

    private Socket chatSocket;
    private ChatServer chatServer;

    private PrintWriter chatOut;
    private BufferedReader chatIn;

    public ChatUserHandler(Socket s, ChatServer cs){
        this.chatSocket = s;
        this.chatServer = cs;
        try {
            chatIn = new BufferedReader(new InputStreamReader(chatSocket.getInputStream()));
            chatOut = new PrintWriter(chatSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String user = chatIn.readLine();
            chatServer.addUser(user);
            chatServer.broadcastUser("Użytkownik " + user + " dołączył do czat-u");
            String chatMessage;
            do{
                chatMessage = chatIn.readLine();
                if (chatMessage.equals("exit")){
                    break;
                }
                chatServer.broadcastUser("[ " + user + " ] " + chatMessage);
            }while(true);
            chatServer.broadcastUser("Użytkownik " + user + " opuścił czat");
            chatServer.removeUser(user, this);
            chatOut.close();
            chatIn.close();
            chatSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){
        this.chatOut.println(message);
    }
}
