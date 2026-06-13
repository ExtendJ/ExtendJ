// .result=COMPILE_FAIL
import java.util.ArrayList;
import java.util.List;


public class Test {
	
	interface A<T> { 
		void method(T t, int i, double j); 
	}
	
	class B {
		public void method(int i, double s) {
			
		}
	}
	
	public void testMethod() {
		A a = B::method;
	}
}