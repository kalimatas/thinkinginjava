package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();
//        ExecutorService es = Executors.newFixedThreadPool(5);
//        ExecutorService es = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++) {
            es.execute(new LiftOff());
        }

        es.shutdown();
    }
}
