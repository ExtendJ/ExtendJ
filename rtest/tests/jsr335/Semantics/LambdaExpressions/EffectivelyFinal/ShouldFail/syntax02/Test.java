// .result=COMPILE_FAIL

public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public void foo(TestInterface t) {
		
	}
	
	//Taken from JSR335 section B, 4.12.4
	void m5(int x) {
		int y;
		if (x == 3) y = 1;
		y = 2;
		foo(() -> x+y);
		// Illegal: y is not effectively final.
	}
	
	public static void main(String[] args) {
		
    }
}