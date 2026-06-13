// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.io.IOException;
import java.util.*;

public class Test {

	public interface A {
		B m() throws IOException;
	}
	
	public class B {
		public B() throws IOException {
			throw new IOException();
		}
	}
	
	public void testMethod() {
		A a = B::new;
		boolean foundException = false;
		try {
			a.m();
		} catch(IOException e) {
			foundException = true;
		}
		testTrue("ConstructorReference", foundException);
	}
	
	public static void main(String[] arg) {
		new Test().testMethod();
	}
}
