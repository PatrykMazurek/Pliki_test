package spider.com.serializacja;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Serializacja {

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
