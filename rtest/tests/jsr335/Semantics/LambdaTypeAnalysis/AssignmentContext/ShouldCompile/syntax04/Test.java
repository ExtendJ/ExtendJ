// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public int functMethod(int a, int b); 
	}
	
	public static void main(String[] args) {
		TestInterface t = (int a, int b) -> {
			if(args.length > 2)
				return a;
			else 
				return b;
		};
    }
}
