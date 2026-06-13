// Overriding method from interface of abstract supertype with incompatible return type
// .result=COMPILE_FAIL

interface I {
    int m();
}

abstract class S1 implements I {

    public int m() {
        return 0;
    }
}

abstract class S2 extends S1 {}

public class Test extends S2 {

    public byte m() {
        return 0;
    }
}
