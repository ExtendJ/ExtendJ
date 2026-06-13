// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public void functMethod(int a, int b); 
	}

	
	public static void main(String[] args) {
		int c = 3;
		TestInterface t = (int a, int b) -> {
			if(a + b + c == 3) {
				System.out.println("Found " + c + args[0]);
			}
			else {
				return;
			}
		};
    }
}
