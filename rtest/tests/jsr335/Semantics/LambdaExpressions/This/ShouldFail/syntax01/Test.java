// .result=COMPILE_FAIL


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
	
	public static void main(String[] args) {
		NestedTestInterface t = () -> {
			return () -> this.foo();
		};
    }
}