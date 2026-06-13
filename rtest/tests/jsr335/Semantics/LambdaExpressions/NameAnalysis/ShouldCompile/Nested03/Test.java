// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public void functMethod(); 
	}
	
	public interface NestedTestInterface {
		public TestInterface functMethod();
	}
	
	static int a = 3;
	static int b = 2;
	
	public static void main(String[] args) {
		int c = 3;
		int a = 1;
		NestedTestInterface t = () -> () -> {
			if(a + b > c) {
				System.out.println("Out1");
			}
			else if(a + c < b) {
				System.out.println("Out2");
			}
		};
    }
}
