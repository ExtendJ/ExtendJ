// .result=COMPILE_FAIL

public class Test {
	interface A {
		void m(int a, int b); 
	}
	
	public static void method(A a) {
		
	}
	
	public static void main(String[] args) {
		method((int a, int b) -> a + b);
    }
}