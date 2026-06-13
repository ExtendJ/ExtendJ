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
		boolean wasInstance = false;
		if(b instanceof A) {
			wasInstance = true;
		}
		testTrue("Lambda", wasInstance);
	}
}
