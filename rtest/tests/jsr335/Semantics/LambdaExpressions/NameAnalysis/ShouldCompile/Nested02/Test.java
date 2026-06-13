// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public void functMethod(int a, int b, int c); 
	}
	
	public interface NestedTestInterface {
		public TestInterface functMethod(int a, int b, int c);
	}
	
	public static void main(String[] args) {
		int local = 3;
		NestedTestInterface t = (int a, int b, int c) -> (d, e, f) -> {
			if(local + a > f)
				System.out.println("Out");
		};
    }
}
