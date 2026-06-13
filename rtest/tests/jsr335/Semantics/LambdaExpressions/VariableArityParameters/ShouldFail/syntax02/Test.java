// .result=COMPILE_FAIL

public class Test {
	public interface TestInterface {
		public int functMethod(int a, int[][] b, int c); 
	}
	
	public static void main(String[] args) {
		TestInterface t = (int a, int[]... b, int c) -> a + b[0][0] + c;
    }
}
