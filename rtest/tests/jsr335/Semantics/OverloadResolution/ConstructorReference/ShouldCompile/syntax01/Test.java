// .result=COMPILE_PASS
import java.util.*;

public class Test {
	interface A {
		ArrayList<Integer> m(int a); 
	}
	
	public void method(A a) {
		
	}

	
	public void testMethod() {
		// Basic constructor reference in parameter
		method(ArrayList::new);
	}
	
}