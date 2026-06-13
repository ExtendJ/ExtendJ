// .result=COMPILE_FAIL
public class Test {
	int c;
	
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public Test(int a, int b) {
		c = a + b;
	}
	
	public static int foo(int a) {
		return a + 5;
	}
	
	public Test(int a) {
		this(foo(a = 5), 6);
		TestInterface t = () -> a + 3;
	}
}