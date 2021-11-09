package spider.com.thread.main;

import spider.com.Main;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadTest implements Runnable {

    private int taskNr;
    private UnicNumber unicNumber;
    private Object objLock = new Object();
    private ReentrantLock myLock;

    public ThreadTest(int nr, UnicNumber un, ReentrantLock l){
        this.taskNr = nr;
        this.unicNumber = un;
        this.myLock = l;
    }

    @Override
    public void run() {
        randNumberMyLock();
    }

    public void randNumber(){

        Random rand = new Random();
        int number;
        do{
            number = rand.nextInt(3200);
        }while (unicNumber.checkNumber(number));
        unicNumber.setNumber(number);
//        String thName = Thread.currentThread().getName();
//        System.out.println("Zadanie  " +taskNr+ " wątek " + thName + " wylosował " + number);
//        Main.numberList.add(number);
    }

    public synchronized void randNumberSync(){
        Random rand = new Random();
        int number;
        do{
            number = rand.nextInt(3200);
        }while (unicNumber.checkNumber(number));
        unicNumber.setNumber(number);
    }

    public void randNumberSyncBlock(){
        Random rand = new Random();
        int number;
        synchronized (objLock){
            do{
                number = rand.nextInt(3200);
            }while (unicNumber.checkNumber(number));
            unicNumber.setNumber(number);
        }
    }

    public void randNumberMyLock(){
        Random rand = new Random();
        int number;
        myLock.lock();
        try{
            do{
                number = rand.nextInt(3200);
            }while (unicNumber.checkNumber(number));
            unicNumber.setNumber(number);
        }finally {
            myLock.unlock();
        }
    }
}
