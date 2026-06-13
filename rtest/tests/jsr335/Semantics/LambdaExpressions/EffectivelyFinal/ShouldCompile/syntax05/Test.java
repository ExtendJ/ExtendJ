// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public Exception functMethod(); 
	}
	
	public static void main(String[] args) {
		
		try {
			
		} catch(Exception e) {
			Exception e2 = e;
			TestInterface t = () -> e;
		}
    }
}