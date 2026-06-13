// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.io.IOException;
import java.util.*;

public class Test {
	
	public interface A {
		void m();
	}
	
	public interface B extends A { }


	
	public static void main(String[] arg) {
		B b = () -> { };
		A a = (A)b;
		boolean wasInstance = false;
		if(a instanceof B) {
			wasInstance = true;
		}
		testTrue("Lambda", wasInstance);
	}
}
