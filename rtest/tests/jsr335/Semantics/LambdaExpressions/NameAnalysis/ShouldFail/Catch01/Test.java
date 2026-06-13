// .result=COMPILE_FAIL

public class Test {
	public interface TestInterface {
		public int functMethod(int a); 
	}
	
	public static void main(String[] args) {
		try {
			
		} catch(Exception e) {
			TestInterface t = e -> e + 4;
		}
    }
}
