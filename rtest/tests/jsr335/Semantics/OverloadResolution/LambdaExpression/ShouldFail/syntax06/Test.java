// .result=COMPILE_FAIL

public class Test {
	interface A {
		int m(int a, int b); 
	}
	
	interface B {
		String m(int a, int b);
	}
	
	public static void method(A a, Integer i) {
		
	}
	public static void method(B b, int i) {
		
	}
	
	public static void main(String[] args) {
		method((a, b) -> a + b, 6);
    }
}