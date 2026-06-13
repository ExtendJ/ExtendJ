// .result=COMPILE_FAIL

public class Test {
	public interface TestInterface {
		public void functMethod(int a, int b, int c); 
	}
	
	public interface NestedTestInterface {
		public TestInterface functMethod(int a, int b, int c);
	}
	
	public static void main(String[] args) {
		NestedTestInterface t = (a, b, c) -> {
			TestInterface t2 = (d, e, f) -> {int a = 4; };
		};
    }
}
