// .result=COMPILE_FAIL

public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public void foo(TestInterface t) {
		
	}
	
	//Taken from JSR335 section B, 4.12.4
	void m6(int x) {
		foo(() -> x+1);
		x++;
		// Illegal: x is not effectively final.
	}
	
	public static void main(String[] args) {
		
    }
}