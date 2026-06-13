// .result=COMPILE_FAIL


public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public interface NestedTestInterface {
		public TestInterface functMethod(); 
	}
	
	public static int getInt(int a) {
		return a + 4;
	}
	
	public static void main(String[] args) {
		int a = 3;
		TestInterface t = () -> {
			int b = 6;
			int c = getInt(b = 4);
			return () -> b + a;
		};
    }
	
}