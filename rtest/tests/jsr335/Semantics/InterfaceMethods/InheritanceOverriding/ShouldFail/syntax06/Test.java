// .result=COMPILE_FAIL
public class Test {
	interface A {
		default void m(int i) { }
	}
	
	interface B {
		default void m(int i) { }
	}
	
	class C implements A, B { }
}