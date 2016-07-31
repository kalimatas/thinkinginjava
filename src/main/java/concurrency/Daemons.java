package concurrency;

import java.util.concurrent.TimeUnit;

public class Daemons {

    private static class Daemon implements Runnable {
        private Thread[] threads = new Thread[10];

        @Override
        public void run() {
            for (int i = 0; i < threads.length; i++) {
                threads[i] = new Thread(new DaemonSpawn());
                threads[i].start();
                System.out.println("DaemonSpawn " + i + " started");
            }

            for (int i = 0; i < threads.length; i++) {
                System.out.println("thread " + i + " isDaemon = " + threads[i].isDaemon());
            }

            while (true) Thread.yield();
        }
    }

    private static class DaemonSpawn implements Runnable {
        @Override
        public void run() {
            while (true) Thread.yield();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread d = new Thread(new Daemon());
        d.setDaemon(true);
        d.start();

        TimeUnit.SECONDS.sleep(1);
    }
}
