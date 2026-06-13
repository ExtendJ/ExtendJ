// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public int functMethod(int a, int b, int c); 
	}
	
	public static void main(String[] args) {
		TestInterface t = (a, b, c) -> a + b + c;
    }
}
