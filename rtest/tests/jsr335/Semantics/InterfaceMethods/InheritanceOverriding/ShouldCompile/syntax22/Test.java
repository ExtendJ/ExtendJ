// .result=COMPILE_PASS
public class Test {
	interface A {
		default void m(int i) {  }
	}
	interface B extends A {

	}
	
	class C {
		public void m(int i) { }
	}
	
	class D extends C implements B {

	}
}