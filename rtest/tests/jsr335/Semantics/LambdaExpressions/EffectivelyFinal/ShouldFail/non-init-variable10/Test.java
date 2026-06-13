// .result=COMPILE_FAIL


public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public static void main(String[] args) {
		int a;
		switch(args[0].charAt(0)) {
		case '1': a = 5; break;
		default:
		}
		TestInterface t = () -> a;
    }
	
}