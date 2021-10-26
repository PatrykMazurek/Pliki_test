package spider.com.thread.main;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StartRunnable {

    public StartRunnable(){

        ExecutorService service = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 50; i++){
            service.submit(new RunnableTest(i));
        }
        service.shutdown();
    }
}
