package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Restaurant {

    private static class Meal {
        private final int orderId;

        private Meal(int orderId) {
            this.orderId = orderId;
        }

        @Override
        public String toString() {
            return "Meal#" + orderId;
        }
    }

    private static class Waiter implements Runnable {
        private final Restaurant restaurant;

        private Waiter(Restaurant restaurant) {
            this.restaurant = restaurant;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    synchronized (this) {
                        while (restaurant.meal == null)
                            wait();
                    }

                    System.out.println("Got Meal " + restaurant.meal);

                    synchronized (restaurant.chef) {
                        restaurant.meal = null;
                        restaurant.chef.notifyAll();
                    }

                }
            } catch (InterruptedException e) {
                System.out.println("Interrupted Waiter");
            }
        }
    }

    private static class Chef implements Runnable {
        private final Restaurant restaurant;
        private int count = 0;

        private Chef(Restaurant restaurant) {
            this.restaurant = restaurant;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    synchronized (this) {
                        while (restaurant.meal != null)
                            wait();
                    }

                    if (++count == 10) {
                        System.out.println("Out of food");
                        restaurant.es.shutdownNow();
                    }

                    System.out.println("Order up!");

                    synchronized (restaurant.waiter) {
                        restaurant.meal = new Meal(count);
                        restaurant.waiter.notifyAll();
                    }
                    TimeUnit.MILLISECONDS.sleep(200);
                }
            } catch (InterruptedException e) {
                System.out.println("Interrupted Chef");
            }
        }
    }

    ExecutorService es = Executors.newCachedThreadPool();
    private final Waiter waiter = new Waiter(this);
    private final Chef chef = new Chef(this);
    private Meal meal;

    private Restaurant() {
        es.execute(waiter);
        es.execute(chef);
    }

    public static void main(String[] args) {
        new Restaurant();
    }
}
