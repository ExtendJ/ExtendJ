// .result=COMPILE_FAIL

public class Test {
	
	public static void method(int a, Integer... i) {
		
	}
	public static void method(Number n1, Number n2, Number n3, Integer... i) {
		
	}
	
	public static void main(String[] args) {
		method(new Integer(3), new Integer(2), new Integer(4));
    }
}