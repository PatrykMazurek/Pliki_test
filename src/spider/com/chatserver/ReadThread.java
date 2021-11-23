package spider.com.chatserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadThread extends Thread {

    private BufferedReader cIn;
    private Socket socket;
    private ChatClient client;

    public ReadThread(Socket cSocket, ChatClient cClient){
        this.socket = cSocket;
        this.client = cClient;

        try{
            InputStream in = socket.getInputStream();
            cIn = new BufferedReader(new InputStreamReader(in, "UTf-8"));

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
        String line;
        try{
            do{
                line = cIn.readLine();
                System.out.println(line);
            }while(true);
        } catch (IOException e) {
            System.err.println("Zakończenie pracy");
//            e.printStackTrace();
        }
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
