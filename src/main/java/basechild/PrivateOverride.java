package basechild;

public class PrivateOverride {
    private void f() {
        System.out.println("private f()");
    }

    public static void main(String[] args) {
        PrivateOverride po = new Override();
        po.f(); // private: final, no override
    }
}

class Override extends PrivateOverride {
    public void f() {
        System.out.println("public f()");
    }
}

