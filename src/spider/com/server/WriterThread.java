package spider.com.server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class WriterThread implements Runnable {

    private PrintWriter writer;
    private Socket socket;
    private ChatClient client;

    public WriterThread(Socket socket, ChatClient client){
        this.socket = socket;
        this.client = client;

        try{
            OutputStream out = socket.getOutputStream();
            writer = new PrintWriter(out, true);
        } catch (IOException e) {
            System.out.println("Błąd podczas pobierania obiektu OutputStream " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        sendMessage();
    }

    public void sendMessage(){

    }

    public void stopWriter(){
        if (socket.isConnected()){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
