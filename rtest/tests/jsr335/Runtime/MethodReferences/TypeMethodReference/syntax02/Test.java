// .classpath: @RUNTIME_CLASSES@
import static java.lang.String.valueOf;
import static runtime.Test.*;

import java.util.*;

public class Test {
	
	interface A {
		String m(boolean b);
	}
	
	public static void main(String[] arg) {
		A a = String::valueOf;
		testTrue("MethodRef", a.m(true).equals("true"));
	}
}
