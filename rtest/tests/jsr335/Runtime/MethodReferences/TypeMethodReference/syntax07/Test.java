// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

public class Test {
	int i = 0;
	interface A {
		int m(Test t);
	}
	
	public static int m1(Test t) {
		return t.i;
	}
	
	public int m2() {
		return 4;
	}
	
	public static void main(String[] arg) {
		Test t = new Test();
		t.i = 3;
		boolean bool = true;
		A a = bool ? Test::m1 : Test::m2;
		testTrue("MethodRef", a.m(t) == 3);
		bool = false;
		a = bool ? Test::m1 : Test::m2;
		testTrue("MethodRef", a.m(t) == 4);
	}
}
