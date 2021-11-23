package spider.com.chatserver;

import java.io.IOException;
import java.net.Socket;

public class ChatClient {

    private String userName;

    public ChatClient(String host, int port){
        try {
            Socket socket = new Socket(host, port);
            System.out.println("Nawiazano polączenie z chat-em ");

            new WriterThread(socket, this).start();
            new ReadThread(socket, this).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUserName(String user){
        this.userName = user;
    }

    public String getUserName() {
        return userName;
    }
}
