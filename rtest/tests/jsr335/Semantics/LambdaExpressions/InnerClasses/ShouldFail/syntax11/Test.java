// .result=COMPILE_FAIL


public class Test {	
	public interface TestInterface {
		public void functMethod(int a);
	}
	
	public void foo(int a) {
		
	}
	
	public static void main(String[] args) {
		int a;
		Runnable r = new Runnable() {
			public void run() { 
				a = 7; 
			}
		};

    }

}