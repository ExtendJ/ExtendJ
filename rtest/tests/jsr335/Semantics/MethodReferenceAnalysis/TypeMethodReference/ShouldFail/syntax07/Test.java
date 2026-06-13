// .result=COMPILE_FAIL
import java.util.ArrayList;
import java.util.List;


public class Test {
	
	interface A { 
		void method(B b, int i); 
	}
	
	interface B {
		void method(B b, int i);
	}
	
	public void testMethod() {
		A a = B::method;
	}
}