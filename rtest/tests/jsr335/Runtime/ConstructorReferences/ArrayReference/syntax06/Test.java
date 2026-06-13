// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

public class Test {
	
	interface A {
		String[] m(int i);
	}
	
	public static void main(String[] arg) {
		A a = ((A)((A)String[]::new)::m)::m;
		String[] s = a.m(10);
		s[0] = "a string";
		testTrue("ArrayReference", s.length == 10);
		testTrue("ArrayReference", s[0].equals("a string"));
		testTrue("ArrayReference", s[0] instanceof String);
	}
}
