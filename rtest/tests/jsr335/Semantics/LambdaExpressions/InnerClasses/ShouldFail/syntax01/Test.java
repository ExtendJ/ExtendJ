// .result=COMPILE_FAIL


public class Test {	
	public static void main(String[] args) {
		args = new String[2];
		Runnable r = new Runnable() {
			public void run() { int a = args[0].length(); }
		};
    }

}