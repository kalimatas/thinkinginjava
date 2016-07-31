package generics;

public class ManipulatorBound {
    private static class H {
        public void f() {
            System.out.println("H.f()");
        }
    }

    private static class Manipulator<T extends H> {
        private T object;
        Manipulator(T object) { this.object = object; }
        void manipulate() {
            object.f(); // can call f(), because T is erased to H
        }
    }

    public static void main(String[] args) {
        Manipulator<H> m = new Manipulator<>(new H());
        m.manipulate();
    }
}
