package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AtomicityTest implements Runnable {
    private int i = 0;

    // getValue is not synchronized, so it's possible
    // to read intermediate state
    int getValue() { return i; }

    synchronized void evenIncrement() { i++; i++; }

    @Override
    public void run() {
        while (true) evenIncrement();
    }

    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();
        AtomicityTest at = new AtomicityTest();
        es.execute(at);

        while (true) {
            int val = at.getValue();
            if (val % 2 != 0) {
                System.out.println(val + " is not even!");
                System.exit(0);
            }
        }
    }
}
