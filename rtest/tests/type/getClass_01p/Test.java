// Test that getClass has correct type bound.
// .result=COMPILE_PASS
public class Test {
	static class A {
	}

	void m() {
		A a = new A();
		Class<? extends A> c = a.getClass();
	}
}
