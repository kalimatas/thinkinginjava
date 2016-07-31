package strings;

import java.util.Formatter;

public class MyFormatter {
    private Formatter f;

    /**
     * Constructs a MyFormatter.
     *
     * @param f {@link Formatter} class to use.
     */
    MyFormatter(Formatter f) {
        this.f = f;
    }

    void print(String s) {
        f.format("Argument is %s%n", s);
    }

    public static void main(String[] args) {
        MyFormatter mf = new MyFormatter(new Formatter(System.out));
        mf.print("hello");
        mf.print("bye");
    }
}
