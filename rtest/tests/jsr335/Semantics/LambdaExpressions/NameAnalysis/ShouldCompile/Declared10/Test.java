// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public void functMethod(int a, int b, int c); 
	}
	
	public static void main(String[] args) {
		try {
			
		} catch(Exception a) {
			
		}
		
		TestInterface t = (int a, int b, int c) -> {
			if(a == b + c) {
				System.out.println(a + "Out");
			}
		};
    }
}
