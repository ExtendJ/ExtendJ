// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public static void main(String[] args) {
		int x;
		boolean b = true;
		int y = b ? x = 3 : (x = 5);
		
		TestInterface t = () -> x + y;
    }
}