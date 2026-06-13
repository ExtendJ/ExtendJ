// .result=COMPILE_FAIL


public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public interface NestedTestInterface {
		public TestInterface functMethod(int a, int b);
	}
	
	public static int getInt(int a) {
		return a + 4;
	}
	
	public static void main(String[] args) {
		NestedTestInterface t = (a, b) -> {
			boolean bo = true;
			int c = bo ? 12 : a = 5;
			
			return () -> a + b + c;
		};
    }
	
}