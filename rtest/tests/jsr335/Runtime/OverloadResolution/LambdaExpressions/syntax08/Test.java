// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

public class Test {
	public static int out = 0;
	
	interface A {
		int m(int a, int b); 
	}
	interface B {
		void m(int a, int b);
	}
	
	public static int m() {
		return 5;
	}
	
	public static void method(A a) {
		out = 1;
	}
	
	public static void method(B b) {
		out = 2;
	}
	
	public static void main(String[] args) {
		// Tests most specific method for lambdas, bullet #1
		method((int a, int b) -> m());
		testTrue("Method overload", out == 1);
    }
}
