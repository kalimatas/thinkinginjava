package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FixedDiningPhilosophers {
    public static void main(String[] args) throws InterruptedException {
        int ponder = 1;
        if (args.length > 0)
            ponder = Integer.parseInt(args[0]);

        int size = 3;
        if (args.length > 1)
            size = Integer.parseInt(args[1]);

        Chopstick[] chopsticks = new Chopstick[size];
        for (int i = 0; i < size; i++)
            chopsticks[i] = new Chopstick();

        ExecutorService es = Executors.newCachedThreadPool();
        Philosopher philosopher;
        for (int i = 0; i < size; i++) {
            if (i < (size - 1)) {
                philosopher = new Philosopher(i, chopsticks[i], chopsticks[i+1], ponder);
            } else {
                philosopher = new Philosopher(i, chopsticks[0], chopsticks[i], ponder);
            }
            es.execute(philosopher);
        }

        TimeUnit.SECONDS.sleep(5);

        es.shutdownNow();
    }
}
