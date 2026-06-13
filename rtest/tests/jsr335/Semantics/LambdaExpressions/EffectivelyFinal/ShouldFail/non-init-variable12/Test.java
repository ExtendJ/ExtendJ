// .result=COMPILE_FAIL


public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public static void main(String[] args) {
		int a;
		a = 4;
		TestInterface t = () -> {
			if(args[0].length() == 1) a = 3;
			return 12;
		};
    }
	
}