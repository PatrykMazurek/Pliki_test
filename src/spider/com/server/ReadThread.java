package spider.com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadThread implements Runnable {

    private BufferedReader buffor;
    private Socket socket;
    private ChatClient client;

    public ReadThread(Socket cSocket, ChatClient cClient){
        this.socket = cSocket;
        this.client = cClient;

        try{
            InputStream in = socket.getInputStream();
            buffor = new BufferedReader(new InputStreamReader(in, "UTf-8"));

        } catch (IOException e) {
            System.out.println("błąd podczas pobierania obiektu InputStream " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        readMessage();
    }

    public void readMessage(){

    }

    public void stopReader(){
        if (socket.isConnected()){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
