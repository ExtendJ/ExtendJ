// .result=COMPILE_FAIL
import java.util.*;

public class Test {
	interface A {
		ArrayList<Integer> m(int a); 
	}
	
	interface B {
		ArrayList<Integer> m();
	}
	
	public void method(A a) {
		
	}
	public void method(B b) {
		
	}
	
	public void testMethod() {
		// Ambiguous reference, inexact constructor reference
		method(ArrayList<Integer>::new);
	}
	
}