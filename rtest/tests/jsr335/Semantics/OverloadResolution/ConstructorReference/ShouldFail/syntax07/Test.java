// .result=COMPILE_FAIL
// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.testTrue;

import java.util.*;

public class Test {
	
	interface A {
		Integer m(int i); 
	}
	interface B {
		void m(int i);
	}
	
	public static void method(A a) {
	}
	
	public static void method(B b) {
	}
	
	public static void main(String[] args) {
		// Can't choose most specific method, inexact reference
		method(Integer::new);
    }
}
