// .result=COMPILE_FAIL

public class Test {
	public interface TestInterface {
		 public void m();
	}
	
	public static void main(String[] args) {
		TestInterface t = 5 + () -> {};
    }
}
