package concurrency;

import java.util.concurrent.TimeUnit;

public class LiftOff implements Runnable {
    private static int taskCount = 0;
    private final int id = taskCount++;
    private int countDown = 10;

    LiftOff() {
    }

    LiftOff(int countDown) {
        this.countDown = countDown;
    }

    private void status() {
        System.out.printf("#%d (%s) \n", id, countDown > 0 ? countDown : "Up!");
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (countDown-- > 0) {
            status();
            Thread.yield();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new LiftOff()).start();
        }
        System.out.println("Waiting for loops...");
    }
}
