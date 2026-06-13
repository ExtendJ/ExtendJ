// .result=COMPILE_FAIL

public class Test {
	
	interface A {
		B m(int a, int b);
	}
	
	interface C {
		String m(int a, int b);
	}
	
	interface B {
		int m(int c);
	}
	
	public static void method(A a) {
		
	}
	public static void method(C c) {
		
	}
	
	public static void main(String[] args) {
		// Tests that when a lambda includes any implicit lambdas,
		// phases 1-3 in the algorithm are skipped
		method((int a, int b) -> c -> a + b + c);
    }
}