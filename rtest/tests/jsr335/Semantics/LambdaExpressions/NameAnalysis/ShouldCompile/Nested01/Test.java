// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public int functMethod(int a, int b, int c); 
	}
	
	public interface NestedTestInterface {
		public TestInterface functMethod(int a, int b, int c);
	}
	
	public static void main(String[] args) {
		int local = 3;
		NestedTestInterface t = (a, b, c) -> (d, e, f) -> local + a;
    }
}
