// .result=COMPILE_PASS


public class Test {	
	public static int foo(int a) {
		return a + 2;
	}
	
	public static void main(String[] args) {
		int a;
		int b;
		String s = args[foo(b = a = 4)];

		Runnable r = new Runnable() {
			public void run() { 
				int c = a + b;
			}
		};

    }

}