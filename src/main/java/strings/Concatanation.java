package strings;

public class Concatanation {
    public static void main(String[] args) {

    }

    String implicit(String[] str) {
        String r = "";
        for (String s : str) {
            r += s;
        }
        return r;
    }

    String explicit(String[] str) {
        StringBuilder sb = new StringBuilder();
        for (String s : str) {
            sb.append(s);
        }
        return sb.toString();
    }

    void concat() {
        String s = "hello";
        String ss = "test " + s + " test2";
        System.out.println(ss);
    }
}
