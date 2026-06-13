// .result=COMPILE_PASS

public class Test {
	interface A {
		int m(String a); 
	}
	
	public void method(A a) {
		
	}
	
	
	public void testMethod() {
		// Non-static reference method reference in parameter
		method(String::length);
	}
	
}