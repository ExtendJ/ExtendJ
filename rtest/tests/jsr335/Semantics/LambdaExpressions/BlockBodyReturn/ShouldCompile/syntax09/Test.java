// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public interface NestedTestInterface {
		public TestInterface functMethod();
	}
	
	public static void main(String[] args) {
		NestedTestInterface t = () -> {
			if(args[0].length() == 1) {
				return () -> {
					if(args[0].length() == 1)
						return 4;
					else
						return 1;
				};
			}
			else 
				return () -> {
					if(args[1].length() == 2) 
						return 1; 
					else 
						return 2;  
				};
		};
    }
}