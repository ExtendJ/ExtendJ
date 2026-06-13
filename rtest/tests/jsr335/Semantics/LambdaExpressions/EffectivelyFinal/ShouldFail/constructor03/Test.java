// .result=COMPILE_FAIL
public class Test {
	int c;
	
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public Test(int a, int b) {
		c = a + b;
	}
	
	public Test(int a) {
		this(a = 5, 6);
		TestInterface t = () -> a + 3;
	}
}