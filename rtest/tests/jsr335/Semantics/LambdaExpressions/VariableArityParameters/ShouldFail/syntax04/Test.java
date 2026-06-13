// .result=COMPILE_FAIL

public class Test {
	public interface TestInterface {
		public int functMethod(int[][] a); 
	}
	
	public static void main(String[] args) {
		TestInterface t = (int... a[]) -> a[0][0];
    }
}
