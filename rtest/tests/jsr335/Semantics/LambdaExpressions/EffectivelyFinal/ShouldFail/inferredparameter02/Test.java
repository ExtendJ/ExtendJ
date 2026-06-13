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
			if(args[0].length() == 1)
				int b = getInt(3 + (getInt(a = 5)));
			
			return () -> a + b;
		};
    }
	
}