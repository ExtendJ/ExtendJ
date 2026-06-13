// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

public class Test {
	int out = 0;
	interface A { ArrayList<String> m(int i); }
	interface B { int m(int i); }

	public void method(ArrayList<String> a) { out = 1; }
	public void method(B b) { out = 2; }

	public void testMethod() {
		method(new ArrayList<>());
		testTrue("Overload resolution", out == 1);
	}
	
	public static void main(String[] arg) {
		new Test().testMethod();
	}
	
}
