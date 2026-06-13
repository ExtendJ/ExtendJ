// .result=COMPILE_PASS


public class Test {	
	public static void main(String[] args) {
		try {
			
		} catch(Exception e) {
			Runnable r = new Runnable() {
				public void run() { Exception a = e; }
			};
		}
    }

}