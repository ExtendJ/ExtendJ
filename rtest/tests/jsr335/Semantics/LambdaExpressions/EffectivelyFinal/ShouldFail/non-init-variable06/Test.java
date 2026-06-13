// .result=COMPILE_FAIL


public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public static void main(String[] args) {
		int a;
		int b = 3 + (a = 5) - 5 >> (a = 3);
		TestInterface t = () -> a + b;
    }
	
}