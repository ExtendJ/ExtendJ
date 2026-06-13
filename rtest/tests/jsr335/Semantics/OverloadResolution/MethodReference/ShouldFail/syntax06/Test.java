// .result=COMPILE_FAIL

public class Test {
	interface A {
		int m(int a, int b); 
	}
	
	interface B {
		void m(int a, int b); 
	}
	
	public void method(A a) {
		
	}
	
	public void method(B b) {
		
	}
	
	public int m(int a, int b) {
		
	}
	
	public int m(int a) {
		
	}
	
	public void testMethod() {
		// Amgibuity error, method reference is inexact so no most specific method can be chosen
		method(this::m);
	}
	
}