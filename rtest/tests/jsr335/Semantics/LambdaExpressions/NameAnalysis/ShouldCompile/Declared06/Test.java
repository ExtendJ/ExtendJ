// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public void functMethod(int a, int b, int c); 
	}

	
	public static void main(String[] args) {
		TestInterface t = (int a, int b, int c) -> {
			if(a + b + c == 3) {
				System.out.println("Found " + a);
			}
			else {
				return;
			}
		};
    }
}
