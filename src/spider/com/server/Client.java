package spider.com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private int port;
    private String host;
    private Socket socket;

    public Client(int p, String h){
        this.port = p;
        this.host = h;
    }

    public void startConnection(){
        try {
            InetAddress address = InetAddress.getByName(host);

            socket = new Socket(address, this.port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void talkToServer(){

        // polączenie z serwerem oraz wymiana treści
        try {
            PrintWriter cOut = new PrintWriter(socket.getOutputStream());
            BufferedReader cIn = new BufferedReader(new InputStreamReader( socket.getInputStream()));

            while( true ){
                String reader;
                do{
                    reader = cIn.readLine();
                    System.out.println(reader);
                }while (!reader.isEmpty());
                Scanner scan = new Scanner(System.in);
                String line = scan.nextLine();
                if(line.equals("e")){
                    cOut.println(line);
                    System.out.println(line);
                    break;
                }
                cOut.println(line);
            }
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
