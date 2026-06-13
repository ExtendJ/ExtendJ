// .result=COMPILE_FAIL

public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public static int getInt(int a) {
		TestInterface t = () -> a;
		a = 4;
	}
	
	public static void main(String[] args) {
		
    }
}