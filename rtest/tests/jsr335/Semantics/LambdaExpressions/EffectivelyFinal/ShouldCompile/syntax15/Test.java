// .result=COMPILE_PASS


public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}

	static int a;
	
	public static void main(String[] args) {
		a = 5;
		a = 2;
		
		TestInterface t = () -> a;
    }
	
}