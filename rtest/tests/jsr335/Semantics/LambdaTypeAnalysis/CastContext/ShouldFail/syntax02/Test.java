// .result=COMPILE_FAIL

public class Test {
	
	public interface TestInterface {
		public void m1();
		public void m2();
	}
	
	public static void main(String[] args) {
		TestInterface t = (TestInterface)() -> {};
    }
}
