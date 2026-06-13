// .result=COMPILE_FAIL
import java.util.*;

public class Test {
	interface A {
		ArrayList<Integer> m(int a, String s); 
	}
	
	public void method(A a) {
		
	}
	
	public void testMethod() {
		// No applicable method
		method(ArrayList::new);
	}
	
}