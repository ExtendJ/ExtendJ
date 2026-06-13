// .result=COMPILE_FAIL

public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public interface NestedTestInterface {
		public TestInterface functMethod();
	}

	public static void main(String[] args) {
		int a;
		if(args[0].length() == 1)
			a = 4;
		NestedTestInterface t = () -> () -> a + 4; 
    }
}