// .result=COMPILE_PASS


public class Test {	
	public interface TestInterface {
		public void functMethod(int a);
	}
	
	public void foo(int a) {
		
	}
	
	public static void main(String[] args) {
		int a;
		if(args[0].length() == 1)
			a = 3;
		else 
			a = 5;
		
		TestInterface t = b -> {
			Runnable r = new Runnable() {
				public void run() { int c = a + b; }
			};
		};
    }

}