package spider.com;

import spider.com.serializacja.Person;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        // write your code here

        List<Person> personList = new ArrayList<>();

        personList.add( new Person( "Patryk", "Mazurek", 32, "Kraków",
                "Leśna", 23, "30-321"));
        personList.add( new Person( "Jan", "Kowalski", 56, "Kraków",
                "Długa", 244, "30-361"));
        personList.add( new Person( "Tomasz", "Mazurek", 23, "Kraków",
                "Leśna", 23, "30-321"));

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
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Wiadomość z głównego wątka");


//        System.out.println(f.getAbsolutePath());

//        System.out.println(startLocation);


//

        File[] listFile_1 = Path.of("C:\\Projekty\\test\\").toFile().listFiles();
        //sendToZIPFile(startLocation, "test.zip", listFile_1);

//        readFromZIPFile(Path.of("C:\\Projekty\\test_1\\"),
//                    startLocation.resolve("test.zip").toString());

//        try {
////            Files.createDirectory(Path.of("test"));
//            Path tempF =  Files.createTempFile(null, "txt");
//            Files.move(startLocation.resolve("osoby.dat"), Path.of("C:\\Projekty\\test\\\"osoby.dat\""));
////            Files.copy();
//            System.out.println(tempF.toAbsolutePath());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

/*        if (Files.exists(startLocation.resolve("osoby.dat"))){
            System.out.println("Plik w podanej loklizacji istnieje");
            System.out.println(startLocation.resolve("osoby.dat"));
        }
*/
    }
}
