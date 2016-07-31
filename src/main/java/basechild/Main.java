package basechild;

public class Main {
    protected int x;

    static class Base {
        Base() {
            System.out.println("Base");
        }
    }

    static class Child extends Base {
        Child() {}
    }

    // ----

    static class Base2 {
        protected int x;
        Base2(int x) {
            this.x = x;
            System.out.println("Base2");
        }
    }

    static class Child2 extends Base2 {
        Child2(int x) {
            super(x);
            System.out.println(this.x);
        }
    }

    // ---

    static class T {
        int d(int x) { return 42; }
    }

    static class TT extends T {
        //@Override
        //long d(long x) { return 13L; }
    }

    public static void main(String[] args) {
        new Child();
        new Child2(42);
    }
}
