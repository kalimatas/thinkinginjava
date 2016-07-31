package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EvenChecker implements Runnable {
    private IntGenerator generator;
    private final int id;

    EvenChecker(IntGenerator g, int id) {
        this.generator = g;
        this.id = id;
    }

    @Override
    public void run() {
        while (!generator.isCancelled()) {
            int val = generator.next();
            if (val % 2 != 0) {
                System.out.println(val + " is not even!");
                generator.cancel();
            }
        }
    }

    public static void test(IntGenerator gp, int count) {
        System.out.println("Press Ctrl + C to terminate");

        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < count; i++) {
            es.execute(new EvenChecker(gp, i));
        }
        es.shutdown();
    }

    public static void test(IntGenerator gp) {
        test(gp, 10);
    }
}
