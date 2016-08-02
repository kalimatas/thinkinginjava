package concurrency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CriticalSection {
    static class Pair {
        private int x, y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Pair() { this(0, 0); }

        int getX() { return x; }
        int getY() { return y; }

        void incrementX() { x++; }
        void incrementY() { y++; }

        @Override
        public String toString() {
            return "x: " + x + ", y: " + y;
        }

        class PairIsNotEqualException extends RuntimeException {
            PairIsNotEqualException() {
                super("Pair values are not equal: " + Pair.this);
            }
        }

        void checkState() {
            if (x != y)
                throw new PairIsNotEqualException();
        }
    }

    abstract static class PairManager {
        AtomicInteger counter = new AtomicInteger(0);
        Pair p = new Pair();
        private List<Pair> storage = Collections.synchronizedList(new ArrayList<>());
        synchronized Pair getPair() {
            return new Pair(p.getX(), p.getY());
        }
        void store(Pair p) {
            storage.add(p);
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public abstract void increment();
    }

    // sync entire method
    static class PairManager1 extends PairManager {
        @Override
        public synchronized void increment() {
            p.incrementX();
            p.incrementY();
            store(p);
        }
    }

    // use ctytical section
    static class PairManager2 extends PairManager {
        @Override
        public void increment() {
            Pair temp;

            synchronized (this) {
                p.incrementX();
                p.incrementY();
                temp = getPair();
            }

            store(temp);
        }
    }

    static class PairManipulator implements Runnable {
        private PairManager pm;

        PairManipulator(PairManager pm) {
            this.pm = pm;
        }

        @Override
        public void run() {
            while (true) {
                pm.increment();
            }
        }

        @Override
        public String toString() {
            return "Pair " + pm.getPair() + " checkCounter = " + pm.counter.get();
        }
    }

    static class PairChecker implements Runnable {
        private PairManager pm;

        PairChecker(PairManager pm) {
            this.pm = pm;
        }

        @Override
        public void run() {
            while (true) {
                pm.counter.incrementAndGet();
                pm.getPair().checkState();
            }
        }
    }

    static void testApproaches(PairManager pman1, PairManager pman2) {
        ExecutorService es = Executors.newCachedThreadPool();

        PairManipulator
            pm1 = new PairManipulator(pman1),
            pm2 = new PairManipulator(pman2);

        PairChecker
            pc1 = new PairChecker(pman1),
            pc2 = new PairChecker(pman2);

        es.execute(pm1);
        es.execute(pm2);
        es.execute(pc1);
        es.execute(pc2);

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("pm1: " + pm1 + "\npm2: " + pm2);
        System.exit(0);
    }

    public static void main(String[] args) {
        PairManager
            pman1 = new PairManager1(),
            pman2 = new PairManager2();
        testApproaches(pman1, pman2);
    }
}
