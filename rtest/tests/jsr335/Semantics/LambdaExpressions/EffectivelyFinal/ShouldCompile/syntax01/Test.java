// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public void foo(TestInterface t) {
		
	}
	
	//Taken from JSR335 section B, 4.12.4
	void m1(int x) {
		int y = 1;
		foo((TestInterface)() -> x+y);
		// Legal: x and y are both effectively final.
	}
	
	public static void main(String[] args) {
		
    }
}