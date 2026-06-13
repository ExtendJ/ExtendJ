// .result=COMPILE_PASS


public class Test {	
	public Test(int a, int b) {
		
	}
	
	public static void foo(int a) {
		
	}
	
	public static int foo2(int a) {
		return 5;
	}
	
	public static void main(String[] args) {
		int a;
		switch(args[0].charAt(0)) {
		case 'a' : a = 4; break;
		case 'b' : foo(a = 2); break;
		case 'c' : Test t = new Test(a = 3, 3); break;
		case 'd' : String s = args[a = 0]; break;
		default  : foo(foo2(a = 1));
		}

		Runnable r = new Runnable() {
			public void run() { 
				int b = a + 4;
			}
		};

    }

}