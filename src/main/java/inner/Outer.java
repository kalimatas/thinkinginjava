package inner;

class Outer {
    class Inner {
        int x;
        Inner (int x) {this.x = x;}
    }

    public Inner newInner(int x) {
        return new Inner(x);
    }
}
