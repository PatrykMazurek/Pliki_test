package spider.com;

import java.io.*;
import java.nio.file.Files;
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
        System.out.println(f.getAbsolutePath());
        Path startLocation = f.toPath().toAbsolutePath().getParent();
        System.out.println(startLocation);

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
//        for (File fl : listFile){
//            System.out.println(fl);
//        }

        File[] listFile_1 = Path.of("C:\\Projekty\\test\\").toFile().listFiles();
        //sendToZIPFile(startLocation, "test.zip", listFile_1);

//        readFromZIPFile(Path.of("C:\\Projekty\\test_1\\"),
//                    startLocation.resolve("test.zip").toString());

        try {
//            Files.createDirectory(Path.of("test"));
            Path tempF =  Files.createTempFile(null, "txt");
            Files.move(startLocation.resolve("osoby.dat"), Path.of("C:\\Projekty\\test\\\"osoby.dat\""));
//            Files.copy();
            System.out.println(tempF.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }


/*        if (Files.exists(startLocation.resolve("osoby.dat"))){
            System.out.println("Plik w podanej loklizacji istnieje");
            System.out.println(startLocation.resolve("osoby.dat"));
        }
*/
    }

    public static void sendToZIPFile(Path location, String name, File[] files){

        try {
            FileOutputStream fOut = new FileOutputStream(location.resolve(name).toString());
            ZipOutputStream zipOut = new ZipOutputStream(fOut);
            zipOut.setLevel(Deflater.NO_COMPRESSION);
            zipOut.setMethod(ZipOutputStream.DEFLATED);

            for (File f : files){
                if (f.isFile()){
                    System.out.println("Zapisuje do archiwum " + f.getName());
                    ZipEntry ze = new ZipEntry(f.getName());
                    zipOut.putNextEntry(ze);

                    FileInputStream fin = new FileInputStream(f);
                    byte[] buffer = new byte[2048];
                    int length = 0;
                    while ( (length = fin.read( buffer )) >= 0 ){
                        zipOut.write(buffer, 0, length);
                    }
                    zipOut.closeEntry();
                }
            }
            zipOut.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void readFromZIPFile(Path destLocation, String zipFile ){
        try {
            FileInputStream fInput = new FileInputStream(zipFile);
            ZipInputStream zInput = new ZipInputStream( fInput );
            ZipEntry ze;
            while ( (ze = zInput.getNextEntry()) != null){
                System.out.println("Nazwa " + ze.getName() + " wielkość " + ze.getSize() );
                FileOutputStream fOutput =
                        new FileOutputStream(destLocation.resolve(ze.getName()).toString() );
                byte[] buffer = new byte[2048];
                int length = 0;
                while ( (length = zInput.read( buffer) ) >= 0 ){
                    fOutput.write(buffer, 0, length);
                }
                fOutput.close();
                zInput.closeEntry();
            }
            zInput.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFromFileObject(String fileName){
        List<Person> personList = new ArrayList<>();
        try {
            ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(fileName));

            while (true){
                personList.add((Person) objectIn.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            for (Person p : personList){
                System.out.println(p.toString());
            }
        }
    }


    public static void sendToFileObject(List<Person> personList){
        try {
            ObjectOutputStream objectOut = new ObjectOutputStream(
                    new FileOutputStream("osoby.dat"));

            for (Person p : personList ){
                objectOut.writeObject(p);
            }
            objectOut.close();
            System.out.println("Zapisałem do pliku obiekty");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
