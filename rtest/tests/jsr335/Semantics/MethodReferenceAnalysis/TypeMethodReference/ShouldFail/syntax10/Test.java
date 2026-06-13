// .result=COMPILE_FAIL
import java.util.ArrayList;
import java.util.List;


public class Test {
	
	interface A { 
		void method(B<Number> b, String i); 
	}
	
	class B<T> {
		public void method(T t) {
			
		}
	}
	
	public void testMethod() {
		A a = B::method;
	}
}