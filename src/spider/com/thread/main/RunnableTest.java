package spider.com.thread.main;

import spider.com.Main;

import java.util.Random;

public class RunnableTest extends Thread {

    private int taskNr;

    public RunnableTest(int nr){
        this.taskNr = nr;
    }

    @Override
    public void run() {
        super.run();

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

    }
}
