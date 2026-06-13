// .result=COMPILE_PASS
public class Test {
	interface A {
		default void m() { }
	}
	interface B extends A {
		
	}
	
	interface C extends B {
		
	}
	
	interface D extends C {
		default void m() { }
	}
	
	interface E extends A, D { }
}