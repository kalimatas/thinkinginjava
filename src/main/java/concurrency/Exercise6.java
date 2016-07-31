package concurrency;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Exercise6 {
    public static void main(String[] args) {
        int n = getN();

        ExecutorService es = Executors.newCachedThreadPool();

        for (int i = 0; i < n; i++) {
            int ii = i;
            es.execute(() -> {
                Random r = new Random();
                int sToSleep = r.nextInt(11);

                try {
                    TimeUnit.SECONDS.sleep(sToSleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.printf("Thread #%d slept for %d seconds\n", ii, sToSleep);
            });
        }

        es.shutdown();
    }

    private static int getN() {
        Scanner scanner = new Scanner(System.in);
        int n  = 1;
        try {
            n = scanner.nextInt();
        } catch (Exception e) { }

        return n;
    }
}

