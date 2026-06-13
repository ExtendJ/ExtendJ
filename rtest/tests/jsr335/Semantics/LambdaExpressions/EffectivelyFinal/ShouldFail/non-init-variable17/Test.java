// .result=COMPILE_FAIL


public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public static void foo(int a) {
		
	}
	
	public static void main(String[] args) {
		int a;
		int[][] b = new int[10][10];
		foo(b[a = 1][a = 2]);
		TestInterface t = () -> {
			return a;
		};
    }
	
}