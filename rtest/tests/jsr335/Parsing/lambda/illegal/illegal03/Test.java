// .result=COMPILE_FAIL
public class Test {
	public static void main(String[] args) {
		// Can't have declared parameter without parantheses
		Object f = int b -> b + 3; 
    }
}
