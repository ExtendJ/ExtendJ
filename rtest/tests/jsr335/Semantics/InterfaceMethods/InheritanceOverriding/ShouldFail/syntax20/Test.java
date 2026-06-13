// .result=COMPILE_FAIL
public class Test {
	interface A {
		default void m(int i) {  }
	}
	interface B extends A {
		
	}
	interface C extends B {
		
	}
	
	interface D {
		default void m(int i) {  }
	}
	interface E extends D { }
	interface F extends E { }
	
	class G implements C, F {
		
	}
}