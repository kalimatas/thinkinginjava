package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecptionThread implements Runnable {
    @Override
    public void run() {
        throw new RuntimeException();
    }

    public static void main(String[] args) {
        try {
            ExecutorService es = Executors.newCachedThreadPool();
            es.execute(new ExecptionThread());
        } catch (RuntimeException e) {
            // never caught
            System.out.println("Catched!");
        }
    }
}
