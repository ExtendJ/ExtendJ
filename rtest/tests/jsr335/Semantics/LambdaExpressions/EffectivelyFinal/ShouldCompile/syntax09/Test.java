// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public interface NestedTestInterface {
		public TestInterface functMethod();
	}
	
	public static void main(String[] args) {
		int x;
		if(args[0].length() == 1)
			x = 4;
		else 
			x = 2;
		NestedTestInterface t1 = () -> {
			int z = 4; 
			z = 6;
			z = 2;
			int y = z;
			TestInterface t2 = () -> x + y;
			return t2;
		};
    }
}