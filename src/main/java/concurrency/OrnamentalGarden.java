package concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class OrnamentalGarden {

    private static class Count {
        private int count = 0;
        private Random rand = new Random(42);

        synchronized int increment() {
            int tmp = count;
            if (rand.nextBoolean()) Thread.yield();
            return (count = ++tmp);
        }

        synchronized int get() {
            return count;
        }
    }

    private static class Entrance implements Runnable {
        private final int id;
        private static Count count = new Count();
        private int localCount = 0;
        private static volatile boolean canceled = false;
        private static List<Entrance> entrances = new ArrayList<>();

        Entrance(int id) {
            this.id = id;
            entrances.add(this);
        }

        static void cancel() {
            canceled = true;
        }

        synchronized int get() {
            return localCount;
        }

        static int getTotalCount() {
            return count.get();
        }

        @Override
        public String toString() {
            return "Entrance " + id + ": " + get();
        }

        @Override
        public void run() {
            while (!canceled) {
                synchronized (this) {
                    ++localCount;
                }

                System.out.printf("%s Total: %d\n", this, count.increment());

                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Stopping " + this);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < 3; i++) {
            es.execute(new Entrance(i));
        }

        TimeUnit.SECONDS.sleep(3);

        Entrance.cancel();
        es.shutdown();

        if (!es.awaitTermination(300, TimeUnit.MILLISECONDS)) {
            System.out.println("Cannot terminate");
        }

        System.out.println("Total: " + Entrance.getTotalCount());
    }
}
