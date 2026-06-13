// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

public class Test {
	public static int out = 0;
	
	interface A {
		Number[] m(int a); 
	}
	interface B {
		Integer[] m(int a);
	}

	
	public static void method(A a) {
		out = 1;
	}
	
	public static void method(B b) {
		out = 2;
	}
	
	public static void main(String[] args) {
		// Tests choosing most specific method for method references, bullet #2
		method(Integer[]::new);
		testTrue("Method overload", out == 2);
    }
}
