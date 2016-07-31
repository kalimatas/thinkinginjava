package concurrency;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Exercise11 {
    private static class Pair {
        private int first;
        private int second;

        Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }

        // syncronized!
        synchronized void set(int newValue) {
            first = newValue;
            Thread.yield();
            second = newValue;
        }

        void display() {
            System.out.printf("%d:%d\n", first, second);
        }
    }

    private static class ChangerTask implements Runnable {
        private final Pair pair;

        public ChangerTask(Pair pair) {
            this.pair = pair;
        }

        @Override
        public void run() {
            Random r = new Random();

            while (true) {
                pair.set(r.nextInt(100));
                pair.display();

                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Pair pair = new Pair(0, 0);
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < 3; i++) {
            es.execute(new ChangerTask(pair));
        }
        es.shutdown();
    }
}
