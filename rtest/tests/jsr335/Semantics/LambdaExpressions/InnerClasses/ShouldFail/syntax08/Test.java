// .result=COMPILE_FAIL


public class Test {	
	public interface TestInterface {
		public void functMethod(int a);
	}
	
	public static int foo(int a) {
		return a + 1;
	}
	
	public static void main(String[] args) {
		int a;
		a = foo(a = 3);
		Runnable r = new Runnable() {
			public void run() { int b = a; }
		};
    }

}