// .result=COMPILE_FAIL

public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public static int getInt(int a) {
		boolean b = false;
		int c = b ? 12 : a = 4;
		TestInterface t = () -> c + a;
	}
	
	public static void main(String[] args) {
		
    }
}