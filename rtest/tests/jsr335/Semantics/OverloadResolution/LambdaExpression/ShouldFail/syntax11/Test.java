// .result=COMPILE_FAIL

public class Test {
	interface A {
		int m(int a, int b); 
	}
	
	interface B {
		void m(int a, int b);
	}
	
	public static int m() {
		return 5;
	}
	
	public static void method(A... a) {
		
	}
	public static void method(B b, B... i) {
		
	}
	
	public static void main(String[] args) {
		method((int a, int b) -> m());
    }
}