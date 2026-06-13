// .result=COMPILE_FAIL

public class Test {
	interface A {
		void m(int a, int b); 
	}
	
	interface B {
		void m(int a, int b); 
	}
	
	public void method(A a) {
		
	}
	
	public void method(B b) {
		
	}
	
	public void m(int a, int b) {
		
	}
	
	public void testMethod() {
		// Amgibuity error, two most specific methods exists
		method(this::m);
	}
	
}