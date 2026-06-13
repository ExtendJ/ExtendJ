// .result=COMPILE_FAIL
public class Test {
	public static void main(String[] args) {
		// Can't mix declared and inferred parameters
		Object f = (c, int a, int b) -> a + b; 
    }
}
