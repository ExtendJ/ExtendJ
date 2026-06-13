// .result=COMPILE_FAIL
import java.util.*;

public class Test {
	interface A {
		ArrayList<String> m(); 
	}
	
	
	public void method(A a) {
		
	}
	
	public void testMethod() {
		// Reference not congruent with A
		method(ArrayList<Integer>::new);
	}
	
}