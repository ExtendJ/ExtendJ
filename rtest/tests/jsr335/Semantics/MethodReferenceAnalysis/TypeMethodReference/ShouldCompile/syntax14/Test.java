// .result=COMPILE_PASS
import java.util.*;

public class Test {
	
	interface A { 
		void method(B<Number> b, Double i); 
	}
	
	class B<T> {
		public void method(T t) {
			
		}
	}
	
	public void testMethod() {
		A a = B::method;
	}
}