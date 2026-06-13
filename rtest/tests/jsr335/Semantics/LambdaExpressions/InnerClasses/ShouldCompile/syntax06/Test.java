// .result=COMPILE_PASS


public class Test {	
	public interface TestInterface {
		public void functMethod(int a);
	}
	
	public static void foo(int a) {
		
	}
	
	public static void main(String[] args) {
		int a;
		foo((a = 5));
		
		TestInterface t = b -> {
			Runnable r = new Runnable() {
				public void run() { int c = a + b; }
			};
		};
    }

}