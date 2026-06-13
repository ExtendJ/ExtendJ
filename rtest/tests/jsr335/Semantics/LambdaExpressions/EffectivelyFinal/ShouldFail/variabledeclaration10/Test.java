// .result=COMPILE_FAIL

public class Test {
	public interface TestInterface {
		public void functMethod(); 
	}
	
	public static void main(String[] args) {
		int a = 1;
		a = 2;
		TestInterface t = () -> {
			Runnable r = new Runnable() {
				public void run() {
					TestInterface t2 = () -> {
						Runnable r = new Runnable() {
							public void run() {
								TestInterface t3 = () -> {
									int b = a;
								};
							}
						};
					};
				}
			};
		};
    }
}