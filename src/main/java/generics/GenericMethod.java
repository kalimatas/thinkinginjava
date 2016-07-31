package generics;

public class GenericMethod<U> {
    <T> String f(T x) {
        return x.getClass().getName();
    }

    <T, X, S> void ff(T x, X y, S s) {
        System.out.printf("%s %s %s%n", x.getClass().getName(), y.getClass().getName(), s.getClass().getName());
    }

    void g(U x) {
        System.out.println(x.getClass().getName());
    }

    public static void main(String[] args) {
        GenericMethod<String> g = new GenericMethod<>();
        System.out.println(g.f(2));
        System.out.println(g.f(2.0));
        System.out.println(g.f("hello"));

        g.ff(1, 2.0, "ewfi");

        g.g("type");
    }
}
