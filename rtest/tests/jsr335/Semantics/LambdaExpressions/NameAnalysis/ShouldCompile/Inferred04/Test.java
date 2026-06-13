// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public int functMethod(int a); 
	}
	
	static int b = 2;
	public static void main(String[] args) {
		int c = 3;
		TestInterface t = a -> a + b + c;
    }
}
