// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

public class Test {
	
	interface A {
		int m(ArrayList<Integer> list, int i);
	}
	interface B {
		void m(ArrayList<Integer> list, int i);
	}
	
	public static void main(String[] arg) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		B b = ArrayList<Integer>::add;
		b.m(list, 1);
		b.m(list, 2);
		b.m(list, 3);
		A a = ArrayList::get;
		testTrue("MethodRef", a.m(list, 0) == 1);
		testTrue("MethodRef", a.m(list, 1) == 2);
		testTrue("MethodRef", a.m(list, 2) == 3);
	}
}
