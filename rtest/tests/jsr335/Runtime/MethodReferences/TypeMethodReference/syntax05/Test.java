// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

public class Test {
	
	interface A {
		int m();
	}
	
	public static int method() {
		return 2;
	}
	
	public static class InnerTest {
		public static int method() {
			return 3;
		}
	}
	
	public class Inner extends Test {

		public void testMethod() {
			A a1 = Inner::method;
			A a2 = Test::method;
			A a3 = InnerTest::method;
			testTrue("MethodRef", a1.m() == 2);
			testTrue("MethodRef", a2.m() == 2);
			testTrue("MethodRef", a3.m() == 3);
		}
	}
	
	public static void main(String[] arg) {
		Test t = new Test();
		Inner in = t.new Inner();
		in.testMethod();
		A a = InnerTest::method;
		testTrue("MethodRef", a.m() == 3);
	}
}
