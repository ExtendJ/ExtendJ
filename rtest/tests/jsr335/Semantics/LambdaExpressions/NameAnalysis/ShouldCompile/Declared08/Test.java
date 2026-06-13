// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public void functMethod(int a); 
	}

	static int b = 1;
	public static void main(String[] args) {
		int c = 3;
		TestInterface t = (int a) -> {
			if(a + b + c == 3) {
				System.out.println("Found " + b + args[0]);
			}
			else {
				System.out.println(a + "");
			}
		};
    }
}
