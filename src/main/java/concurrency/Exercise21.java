package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Exercise21 {

    private static class Task1 implements Runnable {
        @Override
        synchronized public void run() {
            try {
                System.out.println("Waiting in Task1");
                wait();
            } catch (InterruptedException e) {
                System.out.println("Interrupted Task1");
            }

            System.out.println("Exiting Task1");
        }
    }

    private static class Task2 implements Runnable {
        private final Task1 task;

        Task2(Task1 task) {
            this.task = task;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(1);

                // Have to acquire lock on task
                synchronized (task) {
                    task.notify();
                }

            } catch (InterruptedException e) {
                System.out.println("Interrupted Task2");
            }

            System.out.println("Exiting Task2");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();
        Task1 task1 = new Task1();
        Task2 task2 = new Task2(task1);

        es.execute(task1);
        es.execute(task2);

        TimeUnit.SECONDS.sleep(3);

        es.shutdownNow();
    }
}
