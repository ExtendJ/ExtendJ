// .result=COMPILE_FAIL


public class Test {	
	public interface TestInterface {
		public void functMethod(int a);
	}
	
	static int a = 5;
	
	public static void main(String[] args) {
		TestInterface t = (int a) -> {
			a++;
			Runnable r = new Runnable() {
				public void run() { int b = a; }
			};
		};
    }

}