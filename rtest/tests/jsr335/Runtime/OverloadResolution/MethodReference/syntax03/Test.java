// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

public class Test {
	public static int out = 0;
	
	interface A {
		int m(String a); 
	}
	interface B {
		void m(String a);
	}
	
	
	public static void method(A a) {
		out = 1;
	}
	
	public static void method(B b) {
		out = 2;
	}
	
	public static void main(String[] args) {
		// Tests choosing most specific method for method references, bullet #1
		method(String::length);
		testTrue("Method overload", out == 1);
    }
}
