// .result=COMPILE_FAIL


public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public interface NestedTestInterface {
		public TestInterface functMethod(int a, int b);
	}
	
	public static void main(String[] args) {
		NestedTestInterface t = (a, b) -> {
			if(args[0].length() == 1)
				a = 4;
			return () -> a + b;
		};
    }
	
}