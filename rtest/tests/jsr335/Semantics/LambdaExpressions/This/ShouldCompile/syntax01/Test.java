// .result=COMPILE_PASS


public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public interface NestedTestInterface {
		public TestInterface functMethod();
	}
	
	public int foo() {
		return 4;
	}

	public void someMethod() {
		NestedTestInterface t = () -> {
			return () -> this.foo();
		};
	}
	
	public static void main(String[] args) {

    }
}