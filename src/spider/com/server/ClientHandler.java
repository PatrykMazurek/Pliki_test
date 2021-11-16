package spider.com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;
    private PrintWriter chOut;
    private BufferedReader chIn;

    public ClientHandler(Socket s){
        this.socket = s;
        System.out.println("Połączono z adresem " + socket.getInetAddress());

        try {
            chOut = new PrintWriter(socket.getOutputStream());
            chIn = new BufferedReader(new InputStreamReader( socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {

        boolean work = true;
        String reader;
        String message = " Wybierz opcję: \n" +
                "1) cok 1\n" +
                "2) cok 2\n" +
                "e) zakończ";

        try {
            while(work){
                chOut.println(message);
                reader = chIn.readLine();
//                System.out.println(reader);
                switch (reader){
                    case "1":
                        chOut.println("wiadomość 1");
                        break;
                    case "2":
                        chOut.println("wiadomość 2");
                        break;
                    case "e":
                        chOut.println("zakończenie");
                        work = false;
                        break;
                    default:
                        chOut.println("Nie podano poprawnej opcji");
                        break;
                }
            }
            stopConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopConnection(){
        if(socket.isConnected()){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
