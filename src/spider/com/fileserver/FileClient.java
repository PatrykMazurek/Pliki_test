package spider.com.fileserver;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.util.Scanner;

public class FileClient {

    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Socket socket;
    private String defaultLocation;
    private Scanner scan;

    public FileClient(String host, int port){
        try{
            socket = new Socket(host, port);
            scan = new Scanner(System.in);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            defaultLocation = "C:\\Projekty\\test\\";

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startConnection() {
        boolean work = true;
        while (work){

            try {
                int messageSize = dataInputStream.readInt();
                byte[] messageBytes = new byte[messageSize];
                dataInputStream.readFully(messageBytes, 0 , messageSize);

                String message = new String(messageBytes, "UTF-8");
                System.out.println(message);

                char[] decision = scan.nextLine().toCharArray();

                dataOutputStream.writeChar(decision[0]);

                switch (decision[0]){

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void stopConnection() {}

    public void uploadFileToServer(){
        File[] listFile = Path.of(defaultLocation).toFile().listFiles();

        for ( int i = 0; i< listFile.length; i++){
            System.out.println(i + " ) " + listFile[i].getName());
        }
        int fileIndex = scan.nextInt();
        File fileToSend = listFile[fileIndex];

        try {
            byte[] fileNameByte = fileToSend.getName().getBytes("UTF-8");


            long filelength = fileToSend.length();
            byte[] fileContentByte = new byte[(int) filelength];
            FileInputStream fileIn = new FileInputStream(fileToSend);
            fileIn.read(fileContentByte);

            // wysłanie nazy pliku
            dataOutputStream.writeInt(fileToSend.getName().length());
            dataOutputStream.write(fileNameByte);
            // wysyłanie zawartości pliku
            dataOutputStream.writeLong(filelength);
            dataOutputStream.write(fileContentByte);

            fileIn.close();

        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void downloadFileFromServer(){
        try{
            int messageSize = dataInputStream.readInt();
            byte[] messageBytes = new byte[messageSize];
            dataInputStream.readFully(messageBytes, 0 , messageSize);

            String message = new String(messageBytes, "UTF-8");
            System.out.println(message);

            int decision = scan.nextInt();
            dataOutputStream.writeInt(decision);

            int fileNameSize = dataInputStream.readInt();
            if(fileNameSize > 0 ){
                // odbieram nazwę pliku
            }else{
                System.out.println("Serwer nie przkazał pliku");
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
