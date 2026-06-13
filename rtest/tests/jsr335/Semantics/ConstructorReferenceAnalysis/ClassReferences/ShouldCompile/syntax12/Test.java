// .result=COMPILE_PASS
import java.util.ArrayList;
import java.util.List;
import java.io.*;


public class Test {
	
	interface A {
		B m(String s);
	}
	
	class B<T> {
		public B(T t)  {
			
		}
	}
	
	public void testMethod() {
		A a = B<String>::new;
	}

}