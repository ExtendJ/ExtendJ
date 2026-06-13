// .result=COMPILE_FAIL

public class Test {
	interface A {
		int m(int a, short b); 
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
	
	public void testMethod() {
		// Amgibuity error, parameters in function descriptors are not equal so no most specific method can be chosen
		method(this::m);
	}
	
}