// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

public class Test {
	public static int out = 0;
	
	interface A {
		void m(int a, int b); 
	}
	
	public static void method(A... as) {
		out = 1;
	}
	
	public static void method(A a) {
		out = 2;
	}
	
	public static void main(String[] args) {
		// Tests that fixed arity type is preferred over variable arity type
		method((int a, int b) -> { });
		testTrue("Method overload", out == 2);
		
		// Tests that array type matches variable arity type
		A[] as = {(a, b) -> { }};
		method(as);
		testTrue("Method overload", out == 1);
    }
}
