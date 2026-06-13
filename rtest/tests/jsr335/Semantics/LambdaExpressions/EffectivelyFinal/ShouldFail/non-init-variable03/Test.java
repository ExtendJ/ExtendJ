// .result=COMPILE_FAIL


public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public static void main(String[] args) {
		int a;
		for(int i = 0; i < 100; i++) {
			a = i;
		}
		
		TestInterface t = () -> a;
    }
	
}