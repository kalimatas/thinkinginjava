package nested;

public class Main {
    private static class Nes {
        private static class NesNes {
            private static final int X = 42;
        }
    }

    private class Inner {

    }

    public static void main(String[] args) {
        Nes n = new Nes();
        Nes.NesNes nn = new Nes.NesNes();

        Inner i = new Main().new Inner();
    }
}
