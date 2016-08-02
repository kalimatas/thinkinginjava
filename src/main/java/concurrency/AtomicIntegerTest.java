package concurrency;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest implements Runnable {
    private AtomicInteger ai = new AtomicInteger();

    int get() { return ai.get(); }

    void evenIncrement() { ai.addAndGet(2); }

    @Override
    public void run() {
        while (true) evenIncrement();
    }

    public static void main(String[] args) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.err.println("Aborting");
                System.exit(0);
            }
        }, 5000);

        ExecutorService es = Executors.newCachedThreadPool();
        AtomicIntegerTest ait = new AtomicIntegerTest();
        es.execute(ait);

        while (true) {
            int val = ait.get();
            if (val % 2 != 0) {
                System.out.printf(val + " is not even!");
                System.exit(0);
            }
        }
    }
}
