// .result=COMPILE_FAIL

public class Test {
	public interface TestInterface {
		 public int m();
	}
	
	public static void main(String[] args) {
		TestInterface t = args.length==4 ? () -> 4 : () -> {}; 
    }
}
