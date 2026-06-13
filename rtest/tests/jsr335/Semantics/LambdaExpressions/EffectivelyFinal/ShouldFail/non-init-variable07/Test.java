// .result=COMPILE_FAIL


public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public static int getInt(int a) {
		return a + 5;
	}
	
	public static void main(String[] args) {
		int a;
		int b = 3 + getInt(1 + (a = 5));
		a = 6;
		TestInterface t = () -> a + b;
    }
	
}