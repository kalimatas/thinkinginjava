package concurrency;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PipedIO {

    private static class Sender implements Runnable {
        private PipedWriter writer = new PipedWriter();

        @Override
        public void run() {
            try {
                while (true) {
                    for (char c = 'A'; c <= 'Z'; c++) {
                        writer.write(c);
                        TimeUnit.MILLISECONDS.sleep(400);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                System.out.println("Interrupted Sender");
            }
        }

        private PipedWriter getWriter() {
            return writer;
        }
    }

    private static class Receiver implements Runnable {
        private PipedReader reader;

        Receiver(Sender sender) throws IOException {
            reader = new PipedReader(sender.getWriter());
        }

        @Override
        public void run() {
            try {
                while (true) {
                    System.out.println("read: " + (char)reader.read());
                }
            } catch (IOException e) {
                System.out.println(e + " Receiver read exception");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Sender sender = new Sender();
        Receiver receiver = new Receiver(sender);

        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(sender);
        es.execute(receiver);

        TimeUnit.SECONDS.sleep(4);

        es.shutdownNow();
    }
}
