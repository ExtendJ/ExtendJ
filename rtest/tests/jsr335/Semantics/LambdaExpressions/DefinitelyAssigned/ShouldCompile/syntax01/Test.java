// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public static void main(String[] args) {
		int a;
		if(args[0].length() == 1)
			a = 4;
		else
			a = 3;
		TestInterface t = () -> { if (a == 3) return a + 4; return a; };
    }
}