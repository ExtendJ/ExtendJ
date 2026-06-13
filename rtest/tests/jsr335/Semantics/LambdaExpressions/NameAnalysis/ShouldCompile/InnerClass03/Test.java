// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public void functMethod(int a, int b, int c); 
	}
	
	public interface NestedTestInterface {
		public TestInterface functMethod(int a, int b, int c); 
	}
	
	public static void main(String[] args) {
		NestedTestInterface t1 = (a, b, c) -> new TestInterface() {
			@Override
			public void functMethod(int a, int b, int c) {
				if(a == c + b) {
					System.out.println("Out");
			    }
			}
		};
    }
}
