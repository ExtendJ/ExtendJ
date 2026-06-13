// .result=COMPILE_FAIL

public class Test {
	public interface TestInterface {
		public int functMethod(int a, int b, int c); 
	}
	
	public static void main(String[] args) {
		int a = 4;
		
		TestInterface t = (a, b, c) -> a + b + c;
    }
}
