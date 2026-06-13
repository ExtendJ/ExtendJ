// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public int functMethod(int a, int b); 
	}
	
	static int a = 3;
	static int b = 2;
	
	public static void main(String[] args) {
		int c = 3;
		TestInterface t = (int a, int b) -> a + b + c;
    }
}
