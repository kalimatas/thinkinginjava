package concurrency;

class Sleeper extends Thread {
    private int duration;
    Sleeper(String name, int duration) {
        super(name);
        this.duration = duration;
        start();
    }

    @Override
    public void run() {
        try {
            sleep(duration);
        } catch (InterruptedException e) {
            System.out.printf("%s was interrupted\n", getName());
        }

        System.out.printf("%s has awakened\n", getName());
    }
}

class Joiner extends Thread {
    private Sleeper sleeper;
    Joiner(String name, Sleeper sleeper) {
        super(name);
        this.sleeper = sleeper;
        start();
    }

    @Override
    public void run() {
        try {
            sleeper.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }

        System.out.printf("%s join completed\n", getName());
    }
}

public class Joining {
    public static void main(String[] args) {
        Sleeper s1 = new Sleeper("Sleepy", 1500);
        Sleeper s2 = new Sleeper("Grumpy", 1500);

        Joiner j1 = new Joiner("j1", s1);
        Joiner j2 = new Joiner("j2", s2);

        s2.interrupt();
    }
}
