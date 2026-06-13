// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

public class Test {
	public static int out = 0;
	
	interface A {
		void m(int a, int b); 
	}
	
	interface B {
		void m(int a, String b);
	}
	
	public static void method(int i, A a) {
		out = 1;
	}
	
	public static void method(Integer i, B a) {
		out = 2;
	}
	
	public static void main(String[] args) {
		// Tests that declared lambda uses congruency to influence the choice during phase 1-2
		method(4, (int a, String b) -> { });
		testTrue("Method overload", out == 2);
		
		// Tests that an inferred lambda do not influence the choice during phase 1-2
		method(4, (a, b) -> { });
		testTrue("Method overload", out == 1);
    }
}
