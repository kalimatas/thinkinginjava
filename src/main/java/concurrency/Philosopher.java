package concurrency;

import java.util.Random;
import java.util.concurrent.TimeUnit;

class Philosopher implements Runnable {
    private final int id;
    private final Chopstick left;
    private final Chopstick right;
    private final int ponder;
    private final Random rand = new Random(42);

    Philosopher(int id, Chopstick left, Chopstick right, int ponder) {
        this.id = id;
        this.left = left;
        this.right = right;
        this.ponder = ponder;
    }

    private void pause() throws InterruptedException {
        if (ponder == 0)
            return;

        TimeUnit.MILLISECONDS.sleep(rand.nextInt(200 * ponder));
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                System.out.println(this + " is thinking");
                pause();
                System.out.println(this + " is grabbing right");
                right.take();
                System.out.println(this + " is grabbing left");
                left.take();
                System.out.println(this + " is eating");
                pause();
                right.drop();
                left.drop();
            }
        } catch (InterruptedException e) {
            System.out.println(this + " was interrupted");
        }
    }

    @Override
    public String toString() {
        return "Ph#" + id;
    }
}
