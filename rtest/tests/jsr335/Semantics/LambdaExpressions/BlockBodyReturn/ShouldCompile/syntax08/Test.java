// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public void functMethod(); 
	}
	
	public interface NestedTestInterface {
		public TestInterface functMethod();
	}
	
	public static void main(String[] args) {
		NestedTestInterface t = () -> {
			if(args[0].length() == 1) {
				return () -> {if(args[1].length() == 2) return; else System.out.println("out"); };
			}
			else 
				return () -> {if(args[1].length() == 2) System.out.println("out"); else return;  };
		};
    }
}