package inner;

public class Main {
    public static void main(String[] args) {
        Outer.Inner in = new Outer().newInner(42);
        System.out.printf("%d%n", in.x);

        IncrementorInterface i = new Incrementor().incrementor();
        System.out.printf("%d%n", i.incr());
        System.out.printf("%d%n", i.incr());

        Incrementor.Inner ii = new Incrementor().new Inner();
        Incrementor iii = ii.incrementor();

        System.out.printf("%d%n", iii.test(24).value());
        System.out.printf("%d%n", iii.testV2(24).value());
    }
}
