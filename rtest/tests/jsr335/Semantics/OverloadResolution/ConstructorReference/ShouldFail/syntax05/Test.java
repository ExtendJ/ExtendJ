// .result=COMPILE_FAIL
import java.util.*;

public class Test {
	interface A {
		int[] m(int a); 
	}
	
	
	public void method(A a) {
		
	}
	
	public void testMethod() {
		// Reference not congruent with A
		method(int[][]::new);
	}
	
}