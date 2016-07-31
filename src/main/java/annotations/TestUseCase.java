package annotations;

public class TestUseCase {
    @UseCase(id = 42, description = "42 desc")
    public void first() {
        System.out.println("first");
    }

    @UseCase(id = 43)
    public void second() {
        System.out.println("second");
    }
}
