// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

public class Test {
	public static int out = 0;
	
	interface A {
		C m(int a, int b); 
	}
	interface B {
		D m(int a, int b);
	}
	interface C {
		void m(int a);
	}
	interface D {
		int m(int a);
	}
	
	public static int m() {
		return 4;
	}
	
	public static void method(A a) {
		out = 1;
	}
	
	public static void method(B b) {
		out = 2;
	}
	
	public static void main(String[] args) {
		// Tests most specific method for lambdas, bullet #3
		method((int a, int b) -> {
			if(a < b) 
				return (int c) -> m();
			else
				return (int c) -> c + a;
		});
		testTrue("Method overload", out == 2);
    }
}
