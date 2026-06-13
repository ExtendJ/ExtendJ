// .result=COMPILE_FAIL
import java.util.ArrayList;
import java.io.*;


public class Test {
	
	interface A {
		B<Integer> m(Long s);
	}
	
	class B<T extends Number> {
		public B(T t)  {
			
		}
	}
	
	public void testMethod() {
		A a = B::new;
	}

}