// .result=COMPILE_FAIL

public class Test {
	
	public interface TestInterface {
		public int m(String a, String b);
	}
	
	public static void main(String[] args) {
		TestInterface t = (TestInterface)(a, b) -> {return a + b;};
    }
}
