package spider.com.thread.main;

import spider.com.Main;

import java.util.Random;

public class ThreadTest implements Runnable {

    private int taskNr;
    private UnicNumber unicNumber;

    public ThreadTest(int nr, UnicNumber un){
        this.taskNr = nr;
        this.unicNumber = un;
    }

    @Override
    public void run() {
        randNumber();
    }

    public void randNumber(){

        Random rand = new Random();
        int number;
        do{
            number = rand.nextInt(350);
        }while (unicNumber.checkNumber(number));
        unicNumber.setNumber(number);
        String thName = Thread.currentThread().getName();
        System.out.println("Zadanie  " +taskNr+ " wątek " + thName + " wylosował " + number);
//        Main.numberList.add(number);
    }

}
