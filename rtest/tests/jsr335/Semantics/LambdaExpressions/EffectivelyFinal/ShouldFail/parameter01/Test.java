// .result=COMPILE_FAIL

public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public static int getInt(int a) {
		if(a == 0)
			return 0;
		int b = getInt(4 + (a = a - 1));
		TestInterface t = () -> b + a;
	}
	
	public static void main(String[] args) {
		
    }
}