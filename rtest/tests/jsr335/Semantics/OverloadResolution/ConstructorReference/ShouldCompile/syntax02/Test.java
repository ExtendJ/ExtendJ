// .result=COMPILE_PASS
import java.util.*;

public class Test {
	interface A {
		int[][] m(int a); 
	}
	
	public void method(A a) {
		
	}

	
	public void testMethod() {
		// Basic array reference in parameter
		method(int[][]::new);
	}
	
}