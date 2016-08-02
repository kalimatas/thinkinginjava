package concurrency;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadLocalHolder {

    static class Accessor implements Runnable {
        private final int id;

        Accessor(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                ThreadLocalHolder.increment();
                System.out.println(this);
                Thread.yield();
            }
        }

        @Override
        public String toString() {
            return "#" + id + ": " + ThreadLocalHolder.get();
        }
    }

    // ----
    private static ThreadLocal<Integer> value = new ThreadLocal<Integer>() {
        private Random rand = new Random(42);

        @Override
        protected synchronized Integer initialValue() {
            return rand.nextInt(1000);
        }
    };

    static void increment() {
        value.set(value.get() + 1);
    }

    static int get() {
        return value.get();
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i <= 5; i++) {
            es.execute(new Accessor(i));
        }
        TimeUnit.SECONDS.sleep(3);
        es.shutdownNow();
    }
}
