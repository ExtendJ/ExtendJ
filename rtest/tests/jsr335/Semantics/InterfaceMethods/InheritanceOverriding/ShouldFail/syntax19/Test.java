// .result=COMPILE_FAIL
public class Test {
	interface A {
		default void m(int i) {  }
	}
	interface B  {
		default void m(int i) { }
	}
	
	class C {
		private void m(int i) { }
	}
	
	class D extends C implements A, B {

	}
}