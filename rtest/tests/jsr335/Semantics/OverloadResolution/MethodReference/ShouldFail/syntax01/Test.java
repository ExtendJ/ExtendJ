// .result=COMPILE_FAIL

public class Test {
	interface A {
		int m(int a); 
	}
	
	public void method(A a) {
		
	}
	
	public static int m(int a) {
		return a;
	}
	
	public void testMethod() {
		// Not potentially applicable
		method(this::m);
	}
	
}