package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import static java.lang.System.out;

public class Exercise1 implements Runnable {
    public Exercise1() {
        out.println("In the constructor");
    }

    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();

        IntStream.range(1, 5)
            .forEach((i) -> es.execute(new Exercise1()) );

        es.shutdown();
    }

    @Override
    public void run() {
        out.println("Entered run 1");
        Thread.yield();
        out.println("Entered run 2");
        Thread.yield();
        out.println("Entered run 3");
        Thread.yield();

        out.println("Finished run");
    }
}
