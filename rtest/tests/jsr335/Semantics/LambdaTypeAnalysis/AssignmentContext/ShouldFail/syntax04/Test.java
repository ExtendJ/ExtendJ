// .result=COMPILE_FAIL

public class Test {
	public interface TestInterface {
		 public int m();
	}
	
	public static void main(String[] args) {
		String s = args[() -> 56];
    }
}
