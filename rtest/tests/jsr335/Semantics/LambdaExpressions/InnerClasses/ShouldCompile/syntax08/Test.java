// .result=COMPILE_PASS


public class Test {	
	public void foo(int a) {
		
	}
	
	public static void main(String[] args) {
		int a;
		int b = 3 + (a = 5);
		Runnable r = new Runnable() {
			int d = a - b;
			public void run() { 
				int c = a + b; 
				d = d - c;
				Runnable r2 = new Runnable() {
					public void run() {
						int e = c + a + b + d;
					}
				};
			}
		};

    }

}