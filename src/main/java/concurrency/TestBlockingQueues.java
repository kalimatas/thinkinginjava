package concurrency;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.SynchronousQueue;

public class TestBlockingQueues {

    private static class LiftOffRunner implements Runnable {
        private BlockingQueue<LiftOff> rockets;

        private LiftOffRunner(BlockingQueue<LiftOff> rockets) {
            this.rockets = rockets;
        }

        private void add(LiftOff rocket) {
            try {
                rockets.put(rocket);
            } catch (InterruptedException e) {
                System.out.println("Interrupted add()");
            }
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    LiftOff rocket = rockets.take();
                    rocket.run();
                }
            } catch (InterruptedException e) {
                System.out.println("Interrupted LiftOff runner.run()");
            }

            System.out.println("Exiting LiftOff runner.run()");
        }
    }

    private static void test(String msg, BlockingQueue<LiftOff> queue) throws IOException {
        LiftOffRunner runner = new LiftOffRunner(queue);
        Thread t = new Thread(runner);
        t.start();

        for (int i = 0; i < 5; i++) {
            runner.add(new LiftOff(5));
        }

        System.out.println("Press");
        InputStreamReader st = new InputStreamReader(System.in);
        st.read();

        t.interrupt();
        System.out.println("Finished");
    }

    public static void main(String[] args) throws IOException {
        test("LinkedBlockingQueue", new LinkedBlockingDeque<>());
        test("ArrayBlockingQueue", new ArrayBlockingQueue<LiftOff>(3));
        test("SynchronousBlockingQueue", new SynchronousQueue<>());
    }
}
