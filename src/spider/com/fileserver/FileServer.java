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
            defaultLocation = "C:\\Projekty\\server\\";

            boolean work = true;

            while(work){
                String message = "1) prześlij plik\n" +
                        "2) pobierz plik \n" +
                        "e) zakończ program";
                int messageSize = message.getBytes(StandardCharsets.UTF_8).length;
                byte[] messageByte = message.getBytes(StandardCharsets.UTF_8);
                dataOutputStream.writeInt(messageSize);
                dataOutputStream.write(messageByte);

                char decision = dataInputStream.readChar();

                switch (decision){
                    case '1':
                        downloadFileFromClient();
                        System.out.println("Powrót do programu głównego server");
                        break;
                    case '2':
                        sendFileToClient();
                        System.out.println("Powrót do programu głównego server");
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

            byte[] messageBytes = message.getBytes("UTF-8");
            dataOutputStream.writeInt(messageBytes.length);
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
            System.out.println("Zakończemie przekazywania pliku do klienta");
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
            System.out.println("Pobieram pliki od klienta");
            int fileNameSize = dataInputStream.readInt();
            if(fileNameSize > 0 ){
                System.out.println("Przekazywanie nazwy pliku");
                // odbieram nazwę pliku
                byte[] fileNameByte = new byte[fileNameSize];
                dataInputStream.readFully(fileNameByte, 0, fileNameSize);
                String fileName = new String(fileNameByte, 0, fileNameSize, "UTf-8");
                System.out.println("Przekazywany plik " +fileName);
                long fileContentSize = dataInputStream.readLong();
                if( fileContentSize > 0){
                    byte[] fileContentBytes = new byte[(int) fileContentSize];
                    dataInputStream.readFully(fileContentBytes, 0, (int)fileContentSize);

                    FileOutputStream fileOut = new FileOutputStream(
                            new File( defaultLocation + fileName ));
                    fileOut.write(fileContentBytes);
                    fileOut.flush();
                    fileOut.close();
                    System.out.println("Zakończyłem pobierać plik");
                }
            }else{
                System.out.println("klient nie przkazał pliku");
            }
        System.out.println("Koniec pobierania pliku");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
