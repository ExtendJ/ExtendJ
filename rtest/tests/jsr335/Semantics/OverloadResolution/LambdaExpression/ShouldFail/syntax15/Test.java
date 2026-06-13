// .result=COMPILE_FAIL

public class Test {
	
	interface A {
		int m(int a, int b);
	}
	
	interface B {
		void m(int a, int b);
	}
	
	public static void method(A a) {
		
	}
	public static void method(B c) {
		
	}
	
	public static void main(String[] args) {
		// Tests that a statement expression body is applicable with both value and void return
		method((a, b) -> System.out.println(a + b));
    }
}