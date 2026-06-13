// .result=COMPILE_FAIL

public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public void foo(TestInterface t) {
		
	}
	
	//Taken from JSR335 section B, 4.12.4
	void m8() {
		int y;
		foo(() -> y=1);
		// Illegal: y is not definitely assigned before the lambda (see 15.27.2)
	}
	
	public static void main(String[] args) {
		
    }
}