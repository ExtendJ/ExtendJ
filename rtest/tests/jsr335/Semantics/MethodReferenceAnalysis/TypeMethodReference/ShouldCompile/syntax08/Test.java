// .result=COMPILE_PASS
import java.util.*;

public class Test {
	
	interface A { 
		long method(B b, int i, String s, ArrayList<Integer> l); 
	}
	
	interface B {
		int method(long i, String s, List<Integer> l);
	}
	
	public void testMethod() {
		A a = B::method;
	}
}