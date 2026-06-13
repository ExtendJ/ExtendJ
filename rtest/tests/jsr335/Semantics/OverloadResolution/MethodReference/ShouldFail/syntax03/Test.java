// .result=COMPILE_FAIL

public class Test {
	interface A {
		String m(String, boolean a); 
	}
	
	public void method(A a) {
		
	}
	
	public void testMethod() {
		// Not potentially applicable
		method(String::valueOf);
	}
	
}