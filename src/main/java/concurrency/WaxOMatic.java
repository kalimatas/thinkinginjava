package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WaxOMatic {

    private static class Car {
        private boolean waxed = false;

        synchronized void waxed() {
            this.waxed = true;
            notifyAll();
        }

        synchronized void buffed() {
            this.waxed = false;
            notifyAll();
        }

        synchronized void waitingForWaxing() throws InterruptedException {
            while (!waxed)
                wait();
        }

        synchronized void waitingForBuffing() throws InterruptedException {
            while (waxed)
                wait();
        }
    }

    private static class WaxOn implements Runnable {
        private final Car car;

        WaxOn(Car car) {
            this.car = car;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    System.out.println("WaxOn!");
                    TimeUnit.MILLISECONDS.sleep(200);
                    car.waxed();
                    car.waitingForBuffing();
                }
            } catch (InterruptedException e) {
                System.out.println("Exiting from WaxOn with interrupt");
            }

            System.out.println("Ending WaxOn task");
        }
    }

    private static class WaxOff implements Runnable {
        private final Car car;

        WaxOff(Car car) {
            this.car = car;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    car.waitingForWaxing();
                    System.out.println("WaxOff!");
                    TimeUnit.MILLISECONDS.sleep(200);
                    car.buffed();
                }
            } catch (InterruptedException e) {
                System.out.println("Exiting from WaxOff with interrupt");
            }

            System.out.println("Ending WaxOff task");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();
        Car car = new Car();
        es.execute(new WaxOn(car));
        es.execute(new WaxOff(car));

        TimeUnit.SECONDS.sleep(3);

        es.shutdownNow();
    }
}
