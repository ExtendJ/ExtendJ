// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public static void main(String[] args) {
		int x;
		int y = 3 + 4 * 5 >> (x = 5) / 6;
		TestInterface t = () -> x + y;
    }
}