// .result=COMPILE_PASS

public class Test {
	
	interface A { 
		long method(float a, String b); 
	}
	
	public static int m(double a, String b) {
		return 5;
	}
	
	public void testMethod() {
		A a = Test::m;
	}
}