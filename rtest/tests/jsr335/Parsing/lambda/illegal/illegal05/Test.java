// .result=COMPILE_FAIL
public class Test {
	public static void main(String[] args) {
		// Can't have statements in a lambda expression body
		Object f = a -> return a + 3; 
    }
}
