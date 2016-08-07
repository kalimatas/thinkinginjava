package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DeadlockingDiningPhilosophers {
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
        for (int i = 0; i < size; i++)
            es.execute(new Philosopher(i, chopsticks[i], chopsticks[(i + 1) % size], ponder));

        TimeUnit.SECONDS.sleep(5);

        es.shutdownNow();
    }
}
