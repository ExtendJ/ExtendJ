// .result=COMPILE_FAIL
public class Test {
	interface A {
		default void m(int i) { }
	}
	
	interface B {
		void m(int i) { }
	}
	
	interface C extends A, B { }
}