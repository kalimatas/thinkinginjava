package inner;

interface IncrementorInterface {
    public int incr();
}

class Test {
    private int x;
    public Test() { }
    public Test(int x) { this.x = x; }
    public int value() { return x; }
}

class Incrementor {
    private int x;

    private class IncrementorImpl implements IncrementorInterface {
        public int incr() {
            return ++x;
        }
    }

    public class Inner {
        /**
         * Creates new Incrementor instance.
         *
         * @return New Incrementor instance. Use it like {@code new Incrementor().new Inner().incrementor()}
         */
        public Incrementor incrementor() {
            return Incrementor.this;
        }
    }

    public IncrementorInterface incrementor() {
        return new IncrementorImpl();
    }

    public IncrementorInterface incrementorV2() {
        return new IncrementorInterface() {
            public int incr() {
                return ++x;
            }
        };
    }

    public Test test(int x) {
        return new Test(x) {
            public int value() {
                return super.value() * 42;
            }
        };
    }

    public Test testV2(final int v) {
        return new Test() {
            private int x = v;

            public int value() { return x; }
        };
    }
}
