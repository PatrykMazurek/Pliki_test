package spider.com.fileserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Scanner;

public class FileServer {

    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Scanner scan;
    private Socket socket;
    private String defaultLocation;

    public FileServer(Socket s){
        try {
            this.socket = s;
            dataInputStream = new DataInputStream (this.socket.getInputStream());
            dataOutputStream = new DataOutputStream( this.socket.getOutputStream());
            defaultLocation = "C:\\Projekty\\test\\";

            boolean work = true;
            String message = "1) prześlij plik\n" +
                    "2) pobierz plik \n" +
                    "e) zakończ program";
            while(work){
                int messageSize = message.length();
                byte[] messageByte = message.getBytes(StandardCharsets.UTF_8);
                dataOutputStream.writeInt(messageSize);
                dataOutputStream.write(messageByte);

                char decision = dataInputStream.readChar();

                switch (decision){
                    case '1':
                        downloadFileFromClient();
                        break;
                    case '2':
                        sendFileToClient();
                        break;
                    case 'e':
                        work = false;
                        break;
                    default:
                        System.out.println("błędna opcja");
                        break;
                }
            }
            stopConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void stopConnection() {
        if (socket.isConnected()){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendFileToClient(){
        File[] listFile = Path.of(defaultLocation).toFile().listFiles();
        String message = "";
        for ( int i = 0; i< listFile.length; i++){
            message += i + " ) " + listFile[i].getName() + "\n";
        }

        try {
            System.out.println(message.length() + " " + message);
            System.out.println("przesyłam informację do klienta");
            byte[] messageBytes = message.getBytes("UTF-8");
            dataOutputStream.writeInt(message.length());
            dataOutputStream.write(messageBytes);

            int fileId = dataInputStream.readInt();
            File fileToSend = listFile[fileId];

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

    public void downloadFileFromClient(){
        try{
//            int messageSize = dataInputStream.readInt();
//            byte[] messageBytes = new byte[messageSize];
//            dataInputStream.readFully(messageBytes, 0 , messageSize);
//
//            String message = new String(messageBytes, "UTF-8");
//            System.out.println(message);

//            int decision = scan.nextInt();
//            dataOutputStream.writeInt(decision);

            int fileNameSize = dataInputStream.readInt();
            if(fileNameSize > 0 ){
                // odbieram nazwę pliku
                byte[] fileNameByte = new byte[fileNameSize];
                dataInputStream.readFully(fileNameByte, 0, fileNameSize);
                String fileName = new String(fileNameByte, "UTf-8");

                long fileContentSize = dataInputStream.readLong();
                if( fileContentSize > 0){
                    byte[] fileContentBytes = new byte[(int) fileContentSize];
                    dataInputStream.readFully(fileContentBytes, 0, (int)fileContentSize);

                    FileOutputStream fileOut = new FileOutputStream(
                            new File( defaultLocation + fileName ));
                    fileOut.write(fileContentBytes);
                    fileOut.flush();
                    fileOut.close();
                }
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
