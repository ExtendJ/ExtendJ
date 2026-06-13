// .result=COMPILE_FAIL

public class Test {
	interface A {
		List<Integer> m(int a, int b); 
	}
	
	public void method(A a) {
		
	}
	
	public static String m(int a, int b) {
		
	}
	
	public void testMethod() {
		// Not congruent with A
		method(Test::m);
	}
	
}