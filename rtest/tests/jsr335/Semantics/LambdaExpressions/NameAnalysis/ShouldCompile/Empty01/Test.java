// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	static int a = 3;
	static int b = 2;
	
	public static void main(String[] args) {
		int c = 3;
		int a = 1;
		TestInterface t = () -> a + b + c;
    }
}
