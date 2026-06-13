// .result=COMPILE_FAIL


public class Test {	
	public static void main(String[] args) {
		try {
			
		} catch(Exception e) {
			Exception e2 = e = new Exception();
			Runnable r = new Runnable() {
				public void run() { Exception a = e; }
			};
		}
    }

}