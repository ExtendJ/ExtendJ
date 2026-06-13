// .result=COMPILE_PASS


public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public static void main(String[] args) {
		int a;
		switch(args[0].charAt(0)) {
		case '1': a = 5; break;
		case '2': a = 3; break;
		default: a = 4;
		}
		TestInterface t = () -> a;
    }
	
}