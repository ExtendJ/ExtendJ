// .result=COMPILE_FAIL
import java.util.ArrayList;
import java.io.*;


public class Test {
	
	interface A {
		B m(Number s);
	}
	
	class B<T> {
		public B(T t)  {
			
		}
	}
	
	public void testMethod() {
		A a = B<Integer>::new;
	}

}