// .result=COMPILE_PASS


public class Test {	
	public static void main(String[] args) {
		Runnable r = new Runnable() {
			public void run() { int a = args[0].length(); }
		};
    }

}