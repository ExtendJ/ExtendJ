// .result=COMPILE_FAIL


public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public Test(int a, int b) {
		
	}
	
	public static void main(String[] args) {
		int a;
		Test t = new Test(a = 2, a = 3);
		TestInterface t = () -> {
			return a;
		};
    }
	
}