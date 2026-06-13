// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

public class Test {
	public static int out = 0;
	
	interface A {
		TopClass m(); 
	}
	interface B {
		SubClass m();
	}

	
	public static void method(A a) {
		out = 1;
	}
	
	public static void method(B b) {
		out = 2;
	}
	
	public class TopClass {
		
	}
	
	public class SubClass extends TopClass {
		
	}
	
	public void testMethod() {
		// Tests choosing most specific method for method references, bullet #2
		method(SubClass::new);
		testTrue("Method overload", out == 2);	
	}
	
	public static void main(String[] args) {
		new Test().testMethod();
    }
}
