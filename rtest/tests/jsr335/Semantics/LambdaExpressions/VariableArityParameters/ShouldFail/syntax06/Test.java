// .result=COMPILE_FAIL

public class Test {
	public interface TestInterface {
		public int functMethod(int a, int[] b); 
	}
	
	public static void main(String[] args) {
		TestInterface t = (a, ...b) -> a + b[0];
    }
}
