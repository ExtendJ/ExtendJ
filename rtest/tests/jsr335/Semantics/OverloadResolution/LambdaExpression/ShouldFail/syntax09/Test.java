// .result=COMPILE_FAIL

public class Test {
	interface A {
		int m(int a); 
	}
	
	public static void method(int i, A... a) {
		
	}
	
	public static void main(String[] args) {
		method(5, (int i) -> i, (int i) -> i + 1, (int i) -> "string");
    }
}