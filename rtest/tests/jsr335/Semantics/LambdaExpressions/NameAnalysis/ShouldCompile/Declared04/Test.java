// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public static void main(String[] args) {
		int c = 3;
		int b = 2;
		int a = 1;
		TestInterface t = () -> a + b + c;
    }
}
