// .result=COMPILE_FAIL

public class Test {
	public interface TestInterface {
		public void functMethod(); 
	}
	
	public static int getInt(int a) {
		TestInterface t = () -> {
			Runnable r = new Runnable() {
				public void run() {
					TestInterface t2 = () -> {
						Runnable r = new Runnable() {
							public void run() {
								TestInterface t3 = () -> {
									a = 2;
								};
							}
						};
					};
				}
			};
		};
	}
	
	public static void main(String[] args) {
		
    }
}