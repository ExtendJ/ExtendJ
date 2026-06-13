// Test that ambiguous method access is reported.
// .result=COMPILE_FAIL
class A {
	public void m(A x, B y) { }
}

class B extends A {
	public void m(B x, A y) { }
}

public class Test {
    void f() {
		B b = new B();
        b.m(b, b);// ambiguous method access!
    }
}
