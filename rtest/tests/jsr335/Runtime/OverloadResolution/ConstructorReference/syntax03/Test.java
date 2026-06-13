// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

public class Test {
	public static int out = 0;
	
	interface A {
		TopClass m(); 
	}
	interface B {
		void m();
	}
	
	public static void method(A a) {
		out = 1;
	}
	
	public static void method(B b) {
		out = 2;
	}
	
	public class TopClass {
		
	}
	
	public void testMethod() {
		// Tests choosing most specific method for method references, bullet #1
		method(TopClass::new);
		testTrue("Method overload", out == 1);
	}
	

	public static void main(String[] args) {
		new Test().testMethod();
		
    }
}
