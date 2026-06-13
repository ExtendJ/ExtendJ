// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public int functMethod(int a); 
	}
	
	public static void main(String[] args) {
		int c = 3;
		int b = 2;
		TestInterface t = (int a) -> a + b + c;
    }
}
