package spider.com.thread.main;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.*;

public class StartCallable {

    public StartCallable(){
        int taskNumber = 0;
        int maxTask = 50;

        ExecutorService service = Executors.newCachedThreadPool();
        ThreadPoolExecutor threadExecutor = (ThreadPoolExecutor) service;
        Deque<Future<Integer>> futureBlockingDeque = new ArrayDeque<>();
        List<Future<Integer>> resultFromThread = new ArrayList<>();

        while (taskNumber < maxTask){
            if (threadExecutor.getActiveCount() < 10){
                CallableTest ct = new CallableTest(taskNumber);
//                resultFromThread.add(service.submit(ct));
                futureBlockingDeque.add(service.submit(ct));
//            service.submit(ct);
                taskNumber++;
            }

        }
        // wykorzytsanie kolejki do zbierania wyników z wątków
        while(!futureBlockingDeque.isEmpty()) {

            int dequeSize = futureBlockingDeque.size();
            for (int i = 0; i < dequeSize; i++) {
                if (futureBlockingDeque.getFirst().isDone()) {
                    try {
                        System.out.println(futureBlockingDeque.pollFirst().get(2000, TimeUnit.MILLISECONDS));
                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        // wykorzytsanie listy do zbirania wynikow z wątków
        while (!resultFromThread.isEmpty()){
            // TODO napisać rozwiązanie które będzie usuwać zakończone zadania z listy
            int listSize = resultFromThread.size();
            for (int i = 0; i<listSize; i++){
                if (resultFromThread.get(i).isDone()){
                    try {
                        System.out.println(resultFromThread.get(i).get(2000, TimeUnit.MILLISECONDS));
                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        // zakończenie pracy z wątkami
        service.shutdown();
//        System.out.println(threadExecutor.getActiveCount());
//        System.out.println(threadExecutor.getMaximumPoolSize());
//        System.out.println(threadExecutor.getCorePoolSize());
    }


}
