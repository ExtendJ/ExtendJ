// .result=COMPILE_PASS


public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public static int getInt(int a) {
		return a + 5;
	}
	
	public static void main(String[] args) {
		int a;
		int b = args[0].length() == 3 ? 3 + getInt(1 + (a = 5)) : 3 >> ((a = 3) + 4) * 2;
		TestInterface t = () -> a + b;
    }
	
}