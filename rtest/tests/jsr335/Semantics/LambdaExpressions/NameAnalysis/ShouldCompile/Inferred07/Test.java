// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public void functMethod(int a); 
	}
	
	public static boolean checkInt(int a, int b, int c) {
		return a + b + c == 2;
	}
	static int b = 4;
	public static void main(String[] args) {
		int c = 3;
		TestInterface t = (a) -> {
			if(checkInt(a, b, c)) {
				System.out.println("" + a);
			}
			else {
				System.out.println("" + c);
			}
		};
    }
}
