package spider.com.thread.main;

import spider.com.Main;

import java.util.Random;
import java.util.concurrent.Callable;

public class CallableTest implements Callable<Integer> {

    private int taskNr;

    public CallableTest(int nr){
        this.taskNr = nr;
    }

    @Override
    public Integer call() throws Exception {

        Random rand = new Random();
        int number = rand.nextInt();
        String thName = Thread.currentThread().getName();
        System.out.println("Zadanie  " +taskNr+ " wątek " + thName + " wylosował " + number);
        Main.numberList.add(number);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return number;
    }
}
