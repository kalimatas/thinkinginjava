package concurrency;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Interrupting {

    private static class SleepBlock implements Runnable {
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("InterruptedException in SleepBlock");
            }
            System.out.println("Exiting SleepBlock.run");
        }
    }

    private static class IOBlocked implements Runnable {
        private final InputStream is;

        IOBlocked(InputStream is) {
            this.is = is;
        }

        @Override
        public void run() {
            try {
                System.out.println("Waiting for IO");
                is.read();
            } catch (IOException e) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Interreupted IOBlocked");
                } else {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Exiting IOBlocked.run");
        }
    }

    private static class NIOBlock implements Runnable {
        private final SocketChannel sc;
        NIOBlock(SocketChannel sc) {
            this.sc = sc;
        }

        @Override
        public void run() {
            try {
                System.out.println("Waiting for read() in " + this);
                sc.read(ByteBuffer.allocate(1));
            } catch (ClosedByInterruptException e) {
                System.out.println("ClosedByInterruptException");
            } catch (AsynchronousCloseException e) {
                System.out.println("AsynchronousCloseException");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Exiting NIOBlock.run");
        }
    }

    private static class SynchronizedBlock implements Runnable {
        SynchronizedBlock() {
            new Thread() {
                @Override
                public void run() {
                    f();
                }
            }.start();
        }

        private synchronized void f() {
            while (true)
                Thread.yield();
        }

        @Override
        public void run() {
            System.out.println("Trying to call f()");
            f();
            System.out.println("Exiting SynchronizedBlock");
        }
    }

    private static ExecutorService es = Executors.newCachedThreadPool();

    private static void test(Runnable r) throws InterruptedException {
        Future<?> f = es.submit(r);
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println("Interrupting " + r.getClass().getName());
        f.cancel(true);
        System.out.println("Interrupt sent to " + r.getClass().getName());
    }

    private static void IOtest() throws IOException, InterruptedException {
        ServerSocket socket = new ServerSocket(9999);
        InputStream is = new Socket("localhost", 9999).getInputStream();

        es.execute(new IOBlocked(is));
        es.execute(new IOBlocked(System.in));

        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println("Shutting down");
        es.shutdownNow();

        TimeUnit.SECONDS.sleep(1);

        System.out.println("Closing socket");
        is.close();

        TimeUnit.SECONDS.sleep(1);

        System.out.println("Closing System.in");
        System.in.close();
    }

    private static void NIOtest() throws IOException, InterruptedException {
        ServerSocket socket = new ServerSocket(9999);
        InetSocketAddress isa = new InetSocketAddress("localhost", 9999);
        SocketChannel sc1 = SocketChannel.open(isa);
        SocketChannel sc2 = SocketChannel.open(isa);

        Future<?> f = es.submit(new NIOBlock(sc1));
        es.execute(new NIOBlock(sc2));
        es.shutdown();

        TimeUnit.SECONDS.sleep(1);

        // interrupt via cancel
        f.cancel(true);

        TimeUnit.SECONDS.sleep(1);

        // Release lock by closing the channel
        sc2.close();
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        NIOtest();
//        IOtest();
        System.exit(0);

        test(new SleepBlock());

        // I/O and Synchronized are uninterruptible blocking
        test(new IOBlocked(System.in));
        test(new SynchronizedBlock());

        TimeUnit.SECONDS.sleep(3);
        System.out.println("Aborting...");
        System.exit(0);
    }
}
