// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public void functMethod(int a, int b, int c); 
	}
	
	public interface NestedTestInterface {
		public TestInterface functMethod(int a, int b, int c);
	}
	
	public static void main(String[] args) {
		int local = 3;
		NestedTestInterface t = (a, b, c) -> (d, e, f) -> {
			Runnable r = new Runnable() {
			    @Override
			    public void run() {
			    	int a = 3;
			    	int b = 4;
			    	int c = 4;
			    	int d = 6;
			    	int e = 7;
			    	int f = 8;
			    }
			};
		};
    }
}
