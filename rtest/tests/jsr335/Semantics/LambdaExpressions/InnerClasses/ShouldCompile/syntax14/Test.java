// .result=COMPILE_PASS


public class Test {		
	public static void main(String[] args) {
		boolean b;
		if(!(b = false)) {
			int a = 2;
		}

		Runnable r = new Runnable() {
			public void run() { 
				boolean c = b;
			}
		};

    }

}