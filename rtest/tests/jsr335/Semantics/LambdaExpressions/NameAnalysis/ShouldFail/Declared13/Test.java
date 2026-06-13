// .result=COMPILE_FAIL

public class Test {
	public interface TestInterface {
		public void functMethod(int a); 
	}
	
	public static void main(String[] args) {
		TestInterface t = (protected int a) -> { };
    }
}
