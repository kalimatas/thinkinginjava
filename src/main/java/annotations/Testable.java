package annotations;

public class Testable {
    public void execute() {
        System.out.println("executing");
    }

    @TestAnnotation void testExecute() {
        execute();
    }

    public static void main(String[] args) {
        Testable t = new Testable();
        t.testExecute();
    }
}
