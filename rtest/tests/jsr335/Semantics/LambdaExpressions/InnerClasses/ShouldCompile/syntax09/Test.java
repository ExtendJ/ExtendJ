// .result=COMPILE_PASS


public class Test {	
	public static int foo(int a) {
		return a + 2;
	}
	
	public static void main(String[] args) {
		int a;
		int b = args[0].length() == 4 ? 3 - (a = 2) : 2 << foo(a = 4);
		Runnable r = new Runnable() {
			public void run() { 
				Runnable r2 = new Runnable() {
					public void run() {
						int c = a + b;
					}
				};
			}
		};

    }

}