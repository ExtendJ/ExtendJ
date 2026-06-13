// .result=COMPILE_FAIL


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
			return () -> a;
			a = 2;
		};
    }
	
}