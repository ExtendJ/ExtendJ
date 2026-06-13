// .result=COMPILE_FAIL

public class Test {
	public interface TestInterface {
		public String functMethod(); 
	}
	
	public interface NestedTestInterface {
		public TestInterface functMethod();
	}
	
	public static void main(String[] args) {
		int a = 4;
		NestedTestInterface t = () -> { 
			int b;
			if(a == 4)
				b = 5;
			if (a == 3) 
				return () -> {
					if(b == 2)
						return "Done";
					return "Not Done";
				};
			return () -> "Not done"; 
		};
    }
}