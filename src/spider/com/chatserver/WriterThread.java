package spider.com.chatserver;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class WriterThread extends Thread {

    private PrintWriter cOut;
    private Socket socket;
    private ChatClient client;

    public WriterThread(Socket socket, ChatClient client){
        this.socket = socket;
        this.client = client;

        try{
            OutputStream out = socket.getOutputStream();
            cOut = new PrintWriter(out, true);
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
        String user;
        Scanner scan = new Scanner(System.in);

        do{
            System.out.println("Podaj nazwę użytkownika ");
            user = scan.nextLine();
        }while (user.isEmpty());
        client.setUserName(user);
        cOut.println(user);
        String message;
        do{
            message = scan.nextLine();
            cOut.println(message);
        }while (!message.equals("exit"));
        cOut.close();
        stopWriter();
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
