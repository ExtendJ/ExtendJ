// .result=COMPILE_FAIL

public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public void foo(TestInterface t) {
		
	}
	
	//Taken from JSR335 section B, 4.12.4
	void m3(int x) {
		int y;
		if (x == 3) y = 1;
		foo(() -> x+y);
		// Illegal: y is effectively final, but not definitely assigned.
	}
	
	public static void main(String[] args) {
		
    }
}