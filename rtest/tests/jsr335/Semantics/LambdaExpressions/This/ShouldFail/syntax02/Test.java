// .result=COMPILE_FAIL


public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public int foo() {
		return 4;
	}
	
	public static void main(String[] args) {
		TestInterface t = () -> this.foo();
    }
}