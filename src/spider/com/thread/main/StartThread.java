package spider.com.thread.main;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class StartThread {

    public StartThread(){
        System.out.println(" ---- ");
        UnicNumber unicNumber = new UnicNumber();
        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

        int maxTask = 350;
        int nrTask = 0;

        while (nrTask < maxTask){
            if (threadPool.getActiveCount() < 4){
                threadPool.execute(new ThreadTest(nrTask, unicNumber));
                nrTask++;
            }
        }

//        for(int i =0; i< 300; i++){
//            threadPool.submit(new ThreadTest(i, unicNumber));
//        }

        threadPool.shutdown();
    }

}
