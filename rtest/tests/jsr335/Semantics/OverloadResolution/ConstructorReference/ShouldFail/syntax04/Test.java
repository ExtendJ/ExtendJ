// .result=COMPILE_FAIL
import java.util.*;

public class Test {
	interface A {
		int[] m(short a); 
	}
	
	interface B {
		int[] m(int i);
	}
	
	public void method(A a) {
		
	}
	public void method(B b) {
		
	}
	
	public void testMethod() {
		// Ambiguous reference, parameters are different in interface methods
		method(int[]::new);
	}
	
}