// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public int functMethod(int a, int b); 
	}
	
	public static int method(int a, int b) {
		return a + b;
	}
	
	public static void main(String[] args) {
		TestInterface t = (int a, int b) ->  method(a, b);
    }
}
