// Test that the class literal has correct type bound.
// .result=COMPILE_PASS
public class Test {
	static class A { }
	Class<? extends A> c = A.class;
}
