// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public interface NestedTestInterface {
		public TestInterface functMethod();
	}
	
	public static void main(String[] args) {
		NestedTestInterface t1 = () -> {
			int x;
			int y = x = 5;
			TestInterface t2 = () -> x + y;
			return t2;
		};
    }
}