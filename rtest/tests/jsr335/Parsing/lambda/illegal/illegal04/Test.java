// .result=COMPILE_FAIL
public class Test {
	public static void main(String[] args) {
		// Can't have several inferred parameters without parantheses
		Object f = a, b -> a + b; 
    }
}
