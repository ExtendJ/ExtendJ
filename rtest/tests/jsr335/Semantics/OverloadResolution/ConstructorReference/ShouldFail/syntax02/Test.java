// .result=COMPILE_FAIL
import java.util.*;

public class Test {
	interface A {
		int[] m(long a); 
	}
	
	public void method(A a) {
		
	}
	
	public void testMethod() {
		// No applicable method
		method(int[]::new);
	}
	
}