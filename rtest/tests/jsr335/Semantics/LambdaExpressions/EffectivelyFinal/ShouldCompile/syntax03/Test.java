// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public void foo(TestInterface t) {
		
	}
	
	//Taken from JSR335 section B, 4.12.4
	void m4(int x) {
		int y;
		if (x == 3) y = 1;
		else y = 2;
		foo((TestInterface)() -> x+y);
		// Legal: x and y are both effectively final.
	}
	
	public static void main(String[] args) {
		
    }
}