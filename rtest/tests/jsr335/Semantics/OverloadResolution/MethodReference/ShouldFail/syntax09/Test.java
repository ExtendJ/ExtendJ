// .result=COMPILE_FAIL

public class Test {
	interface A {
		Queue<Integer> m(int a, int b); 
	}
	interface B {
		List<Integer> m(int a, int b);
	}
	
	public void method(A a) {
		
	}
	
	public void method(B b) {
		
	}
	
	public static LinkedList<Integer> m(int a, int b) {
		return new LinkedList<Integer>();
	}
	
	public void testMethod() {
		// Both methods in interfaces A and B have reference return type so no specific method can be chosen
		method(Test::m);
	}
	
}