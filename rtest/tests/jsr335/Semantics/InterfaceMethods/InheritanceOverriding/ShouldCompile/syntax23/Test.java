// .result=COMPILE_PASS
public class Test {
	interface A {
		default void m(int i) {  }
	}
	interface B  {
		default void m(int i) { }
	}
	
	class C {
		public void m(int i) { }
	}
	
	class D extends C implements A, B {

	}
}