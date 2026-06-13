// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public static int foo(int a) {
		return a + 5;
	}
	
	public static void main(String[] args) {
		int y;
		int x = 3;
		int a = 4 / foo((y = 7) * 5) + 6;
		TestInterface t = () -> x + y;
    }
}