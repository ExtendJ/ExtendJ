// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public void functMethod(); 
	}

	static int b = 1;
	static int c = 2;
	public static void main(String[] args) {
		int c = 3;
		TestInterface t = () -> {
			if(args[0].length() + b + c == 3) {
				System.out.println("Found " + b + args[0]);
			}
			else {
				System.out.println(b + "");
			}
		};
    }
}
