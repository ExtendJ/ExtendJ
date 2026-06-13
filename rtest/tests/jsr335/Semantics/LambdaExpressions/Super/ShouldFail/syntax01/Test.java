// .result=COMPILE_FAIL


public class Test {

	public void someMethod() {
		NestedTestInterface t = () -> {
			return () -> super.foo();
		};
	}
	
	
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public interface NestedTestInterface {
		public TestInterface functMethod();
	}
	
	public int foo() {
		return 4;
	}

	
	
	public static void main(String[] args) {
		
    }
}