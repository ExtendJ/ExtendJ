// .result=COMPILE_FAIL


public class Test {		
	public static int foo(int a) {
		return a + 2;
	}
	
	public static void main(String[] args) {
		boolean b;
		if(!(b = false))
			b = true;

		Runnable r = new Runnable() {
			public void run() { 
				boolean c = b;
			}
		};

    }

}