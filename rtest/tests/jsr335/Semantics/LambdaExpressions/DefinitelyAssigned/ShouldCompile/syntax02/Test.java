// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public interface NestedTestInterface {
		public TestInterface functMethod();
	}
	
	public static void main(String[] args) {
		NestedTestInterface t = () -> { 
			int a;
			if(args[0].length() == 1)
				a = 4;
			else
				a = 3;
			return () -> a;
		};
    }
}