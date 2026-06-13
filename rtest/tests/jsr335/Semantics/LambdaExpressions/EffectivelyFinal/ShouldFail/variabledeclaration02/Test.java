// .result=COMPILE_FAIL


public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public static int getInt(int a) {
		return a + 4;
	}
	
	public static void main(String[] args) {
		int a = 4;
		TestInterface t = () -> a + 5;
		int b = getInt(3 + (a = 4));
    }
	
}