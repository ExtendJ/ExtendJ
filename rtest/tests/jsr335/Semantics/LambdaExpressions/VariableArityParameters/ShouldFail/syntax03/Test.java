// .result=COMPILE_FAIL

public class Test {
	public interface TestInterface {
		public int functMethod(int[][] b, int c); 
	}
	
	public static void main(String[] args) {
		TestInterface t = (int[]... b, int c) -> b[0][0] + c;
    }
}
