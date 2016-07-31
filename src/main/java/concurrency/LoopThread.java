package concurrency;

import java.util.concurrent.TimeUnit;

public class LoopThread implements Runnable {
    private static int taskCount = 0;
    private final int id = taskCount++;
    private int countDown = 10;

    LoopThread() {
    }

    LoopThread(int countDown) {
        this.countDown = countDown;
    }

    private void status() {
        System.out.printf("#%d (%s) ", id, countDown > 0 ? countDown : "Up!");
    }

    @Override
    public void run() {
        while (countDown-- > 0) {
            status();
//            Thread.yield();
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new LoopThread()).start();
        }
        System.out.println("Waiting for loops...");
    }
}
