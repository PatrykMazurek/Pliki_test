package spider.com;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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

/*        File[] listFile = startLocation.toFile().listFiles(new FilenameFilter() {
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
*/
        if (Files.exists(startLocation.resolve("osoby.dat"))){
            System.out.println("Plik w podanej loklizacji istnieje");
            System.out.println(startLocation.resolve("osoby.dat"));
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
