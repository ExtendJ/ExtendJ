// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public void functMethod(int a, int b, int c); 
	}
	
	public static void main(String[] args) {
		int local = 3;
		TestInterface t1 = (int a, int b, int c) -> {
			TestInterface t2 = new TestInterface() {
			    @Override
			    public void functMethod(int a, int b, int c) {
			    	if(a == c + b) {
			    		System.out.println("Out");
			    	}
			    }
			};
		};
    }
}
