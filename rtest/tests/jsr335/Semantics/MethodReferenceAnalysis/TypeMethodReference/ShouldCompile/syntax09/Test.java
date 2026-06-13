// .result=COMPILE_PASS
import java.util.*;

public class Test {
	
	interface A { 
		long method(B b, int i, String s, ArrayList<Integer> l); 
	}
	
	interface B {
		<S, T> int method(S i, T s, List<Integer> l);
	}
	
	public void testMethod() {
		A a = B::<Integer, String>method;
	}
}