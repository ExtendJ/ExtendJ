// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

public class Test {
	public static int out = 0;
	
	interface A {
		int m(int a, int b); 
	}
	
	public static void method(int i, A a) {
		out = 1;
	}
	
	public static void method(Integer i, A a) {
		out = 2;
	}
	
	public static void method(Double d, A a) {
		out = 3;
	}
	
	public static void main(String[] args) {
		// Tests lambda with strict invocation
		method(4, (int a, int b) -> 4);
		testTrue("Method overload", out == 1);
		
		// Tests lambda with loose invocation
		method(4.0, (int a, int b) -> 3);
		testTrue("Method overload", out == 3);
    }
}
