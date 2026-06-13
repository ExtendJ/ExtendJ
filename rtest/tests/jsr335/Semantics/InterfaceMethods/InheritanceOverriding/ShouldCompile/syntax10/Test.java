// .result=COMPILE_PASS
public class Test {
	interface A {
		default void m() { }
	}
	interface B extends A {
		default void m() { }
	}
	
	interface C extends A {
		
	}
	
	interface D extends B, C {
	}
	
	public class E implements D {
		public void someMethod() {
			m();
		}
	}
}