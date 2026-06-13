// .result=COMPILE_FAIL

public class Test {
	public interface TestInterface {
		public String functMethod(); 
	}
	
	public static void main(String[] args) {
		int a;
		if(args[0].length() == 1) 
			a = 4;
		TestInterface t = () -> { 
			if (a == 3) 
				return "done"; 
			return "not done"; 
		};
    }
}