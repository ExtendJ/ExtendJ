// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

public class Test {
	public static int out = 0;
	
	interface A {
		Integer m(); 
	}
	interface B {
		int m();
	}
	
	public int m() {
		return 5;
	}
	
	
	public static void method(A a) {
		out = 1;
	}
	
	public static void method(B b) {
		out = 2;
	}
	
	public static void main(String[] args) {
		// Tests choosing most specific method for method references, bullet #3
		Test t = new Test();
		method(t::m);
		testTrue("Method overload", out == 2);
    }
}
