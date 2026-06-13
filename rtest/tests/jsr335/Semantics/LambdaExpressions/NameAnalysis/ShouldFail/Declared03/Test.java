// .result=COMPILE_FAIL

public class Test {
	public interface TestInterface {
		public int functMethod(int a, int b); 
	}
	
	public static void main(String[] args) {
		int b = 4;
		TestInterface t = (int a, int b) -> a + b;
    }
}
