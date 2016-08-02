package concurrency;

public class SyncObject {

    static class DualSync {
        private final Object lockObject = new Object();

        synchronized void f() {
            for (int i = 0; i < 5; i++) {
                System.out.println("f()");
                Thread.yield();
            }
        }

        void g() {
            synchronized (lockObject) {
                for (int i = 0; i < 5; i++) {
                    System.out.println("g()");
                    Thread.yield();
                }
            }
        }
    }

    public static void main(String[] args) {
        DualSync ds = new DualSync();

        new Thread() {
            @Override
            public void run() {
                ds.f();
            }
        }.start();

        ds.g();
    }
}
