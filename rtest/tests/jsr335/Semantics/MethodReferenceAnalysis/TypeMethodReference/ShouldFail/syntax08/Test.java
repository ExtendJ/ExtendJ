// .result=COMPILE_FAIL
import java.util.ArrayList;
import java.util.List;


public class Test {
	
	interface A { 
		void method(B b, int i, double j); 
	}
	
	class B {
		public void method(int i, String s) {
			
		}
	}
	
	public void testMethod() {
		A a = B::method;
	}
}