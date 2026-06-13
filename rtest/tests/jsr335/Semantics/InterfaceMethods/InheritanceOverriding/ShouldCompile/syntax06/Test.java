// .result=COMPILE_PASS

public class Test {
	interface A {
		static void m() { }
	}
	interface B extends A {
		static void m() { }
	}
	
	interface C extends B {
		static void m() { }
	}
	
	interface D extends C {
		default void m() { }
		default void m2() { m(); }
	}
	
	public class E implements D {
		public void someMethod() {
			A.m();
			B.m();
			C.m();
			m();
			m2();
		}
	}
}