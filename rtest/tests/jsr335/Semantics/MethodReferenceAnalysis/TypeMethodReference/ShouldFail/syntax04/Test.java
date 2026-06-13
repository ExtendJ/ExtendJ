// .result=COMPILE_FAIL
import java.util.ArrayList;
import java.util.List;


public class Test {
	
	interface A { 
		long method(B b, int i, String s, ArrayList<Integer> l); 
	}
	
	interface B {
		int method(long i, String s, List<Integer> l);
	}
	
	interface C extends B { }
	
	public void testMethod() {
		A a = C::method;
	}
}