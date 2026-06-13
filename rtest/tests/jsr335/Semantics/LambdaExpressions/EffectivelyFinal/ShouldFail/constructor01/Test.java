// .result=COMPILE_FAIL

public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public Test(int a) {
		
	}
	
	public class InnerTest extends Test {
		public InnerTest(int a) {
			super(a = 5);
			TestInterface t = () -> a;
		}
	}
	
	public static void main(String[] args) {
		
    }
}