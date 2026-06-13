// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

public class Test {
	interface A1 {
		void set(B b, int i);
	}
	interface A2 {
		int get(B b);
	}
	
	public class B {
		private int i = 0;
		public void manipulateI(int i) {
			this.i = i;
		}
		public int manipulateI() {
			return i;
		}
	}
	
	public static void main(String[] arg) {
		Test t = new Test();
		B b1 = t.new B();
		B b2 = t.new B();
		A1 a1 = B::manipulateI;
		A2 a2 = B::manipulateI;
		a1.set(b1, 4);
		testTrue("MethodReference", a2.get(b1) == 4);
		testTrue("MethodReference", a2.get(b2) == 0);
		a1.set(b2, 6);
		testTrue("MethodReference", a2.get(b1) == 4);
		testTrue("MethodReference", a2.get(b2) == 6);
	}
}
