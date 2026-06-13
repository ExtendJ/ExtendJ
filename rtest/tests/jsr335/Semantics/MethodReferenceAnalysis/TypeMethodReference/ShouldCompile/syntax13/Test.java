// .result=COMPILE_PASS
import java.util.*;

public class Test {
	
	interface A<T> { 
		void method(T t, int i, double j); 
	}
	
	class B {
		public void method(int i, double s) {
			
		}
	}
	
	public void testMethod() {
		A<B> a = B::method;
	}
}