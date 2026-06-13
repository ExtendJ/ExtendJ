// .result=COMPILE_PASS

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
		// Static reference method reference in parameter
		method(Test::m);
	}
	
}