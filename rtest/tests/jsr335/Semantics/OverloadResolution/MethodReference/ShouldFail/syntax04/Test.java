// .result=COMPILE_FAIL

public class Test {
	interface A {
		void m(int a, int b); 
	}
	
	public void method(A a) {
		
	}
	
	public void m(int a) {
		
	}
	
	public void testMethod() {
		// Not congruent with A
		method(this::m);
	}
	
}