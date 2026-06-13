// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public void functMethod(int a, int b, int c); 
	}
	
	public static boolean checkInt(int a, int b, int c) {
		return a + b + c == 2;
	}
	
	public static void main(String[] args) {
		TestInterface t = (a, b, c) -> {
			if(checkInt(a, b, c)) {
				System.out.println("" + a);
			}
			else {
				System.out.println("" + b);
			}
		};
    }
}
