// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public int functMethod(int a, int b); 
	}
	
	public static void main(String[] args) {
		int c = 3;
		TestInterface t = (a, b) -> a + b + c;
    }
}
