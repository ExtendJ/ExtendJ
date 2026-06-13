// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

public class Test {
	public interface A {
		int m(String s);
	}
	public interface B {
		int m(String s);
	}
	
	
	public static void main(String[] arg) {
		Test t = new Test();
		A a = ((B)s -> s.length())::m;
		testTrue(a.m("yes!") == 4);
	}
}
