package spider.com;

import spider.com.myfile.Person;
import spider.com.server.ChatClient;
import spider.com.server.ChatServer;
import spider.com.server.Client;
import spider.com.server.Server;
import spider.com.thread.main.StartCallable;
import spider.com.thread.main.StartRunnable;
import spider.com.thread.main.StartThread;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static List<Integer> numberList;

    public static void main(String[] args) {
        // write your code here

//        readFromFileObject("osoby.dat");
        File f = new File("t.txt");
        Path startLocation = f.toPath().toAbsolutePath().getParent();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Wiadomość z wątka");
                for (int i =0; i<5; i++){
                    File[] listFile = startLocation.toFile().listFiles(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String name) {
                            if (name.endsWith(".dat")){
                                return true;
                            }else if (name.endsWith(".pdf")){
                                return true;
                            }
                            return false;
                        }
                    });
                    for (File fl : listFile){
                        System.out.println(fl);
                    }
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName());
            }
        });
//        t.start();
//        try {
//            t.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        Client c = new Client(9811, "10.0.50.x"); // łączenie z aplikacją serwerową

//        Server server = new Server(9811);
//        ChatServer chatServer = new ChatServer(9811);
//        chatServer.startServer();

        ChatClient chatClient = new ChatClient("10.55.0.41", 9811);


//        Client c = new Client(9811, "10.55.0.41");
//        c.startConnection();
//        c.talkToServer();
//        c.stopConnection();
    }
}
