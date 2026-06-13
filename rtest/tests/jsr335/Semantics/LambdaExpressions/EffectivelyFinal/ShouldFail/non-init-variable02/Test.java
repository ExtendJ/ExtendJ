// .result=COMPILE_FAIL


public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public static void main(String[] args) {
		int a;
		if(args[0].length() == 3)
			a = 5;
		TestInterface t = () -> a;
    }
	
}