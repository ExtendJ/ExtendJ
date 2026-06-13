// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

public class Test {
	public interface A {
		void m(int i);
	}
	
	int i = 0;
	
	public static void main(String[] arg) {
		Test t = new Test();
		A a = i -> {
			t.i = i;
		};
		A a2 = a::m;
		A a3 = a2::m;
		A a4 = a3::m;
		a4.m(4);
		testTrue("MethodRef", t.i == 4);
	}
}
