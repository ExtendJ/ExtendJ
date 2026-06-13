// .result=COMPILE_PASS


public class Test {	
	public Test(int a, int b) {
		
	}
	
	public static void main(String[] args) {
		int a;
		int b;
		Test t = (new Test(a = 2, b = 3));

		Runnable r = new Runnable() {
			public void run() { 
				int c = a + b;
			}
		};

    }

}