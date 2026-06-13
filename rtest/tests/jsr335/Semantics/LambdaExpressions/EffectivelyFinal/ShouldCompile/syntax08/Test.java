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
			int z = 4;
			z = 6;
			z = 2;
			int y = x = z;
			TestInterface t2 = () -> x + y;
			return t2;
		};
    }
}