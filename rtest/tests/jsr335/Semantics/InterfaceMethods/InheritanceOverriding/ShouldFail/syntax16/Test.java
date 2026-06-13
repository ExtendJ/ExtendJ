// .result=COMPILE_FAIL
public class Test {
	interface A  {
		default void m(int i) { System.out.println("" + i + 1); }
	}
	interface B extends A {
		default void m(int i) {  }
	}
	
	interface C extends B {
		default void m(int i) { }
	}
	
	class D implements A, C {
		public void m(int i) {
			A.super.m(4);
		}
	}
}