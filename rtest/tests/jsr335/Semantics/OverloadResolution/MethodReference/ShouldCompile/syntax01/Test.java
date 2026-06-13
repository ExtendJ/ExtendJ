// .result=COMPILE_PASS

public class Test {
	interface A {
		int m(int a); 
	}
	
	public void method(A a) {
		
	}
	
	public int m(int a) {
		return a;
	}
	
	public void testMethod() {
		// Basic expr method reference in parameter
		method(this::m);
	}
	
}