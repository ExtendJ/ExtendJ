// .result=COMPILE_FAIL


public class Test {

	public void someMethod() {
		TestInterface t = () -> super.foo();
	}
	
	
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public int foo() {
		return 4;
	}

	
	
	public static void main(String[] args) {
		
    }
}