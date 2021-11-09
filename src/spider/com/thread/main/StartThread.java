package spider.com.thread.main;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

public class StartThread {

    public StartThread(){
        System.out.println(" ---- ");
        UnicNumber unicNumber = new UnicNumber();
        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(25);
        ReentrantLock lock = new ReentrantLock();
        int maxTask = 350;
        int nrTask = 0;

//        while (nrTask < maxTask){
//            if (threadPool.getActiveCount() < 15){
//                threadPool.execute(new ThreadTest(nrTask, unicNumber));
//                nrTask++;
//            }
//        }

        for(int i =0; i< 3000; i++){
            threadPool.submit(new ThreadTest(i, unicNumber, lock));
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadPool.shutdown();

        unicNumber.showDuplicate();


    }

}
