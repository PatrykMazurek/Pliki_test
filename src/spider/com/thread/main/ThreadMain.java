package spider.com.thread.main;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;

public class ThreadMain {

    public ThreadMain(){
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

        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
