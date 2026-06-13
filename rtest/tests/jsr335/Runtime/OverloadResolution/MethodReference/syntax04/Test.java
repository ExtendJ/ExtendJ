// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

public class Test {
	public static int out = 0;
	
	interface A {
		List<Integer> m(int a); 
	}
	interface B {
		ArrayList<Integer> m(int a);
	}
	
	public ArrayList<Integer> m(int a) {
		return new ArrayList<Integer>();
	}
	
	
	public static void method(A a) {
		out = 1;
	}
	
	public static void method(B b) {
		out = 2;
	}
	
	public static void main(String[] args) {
		// Tests choosing most specific method for method references, bullet #2
		Test t = new Test();
		method(t::m);
		testTrue("Method overload", out == 2);
    }
}
