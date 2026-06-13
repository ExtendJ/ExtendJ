// .result=COMPILE_PASS


public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public int foo() {
		return 4;
	}

	public void someMethod() {
		TestInterface t = () -> this.foo();
	}
	
	public static void main(String[] args) {

    }
}