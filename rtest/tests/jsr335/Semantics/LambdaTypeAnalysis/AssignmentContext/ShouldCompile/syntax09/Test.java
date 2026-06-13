// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public int functMethod(int a, double b); 
	}
	
	public static int method(int a, double b) {
		return a;
	}
	
	public static void main(String[] args) {
		TestInterface t = (a, b) ->  method(a, b);
    }
}
