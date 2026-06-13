// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;
import java.lang.*;

public class Test {
	
	public int field = 3;
	public interface A {
		public B m(int i);
	}
	
	public interface B {
		public C m(String s, int i);
	}
	
	public interface C {
		public boolean m();
	}
	
	public A getA() {
		return field -> (s, i2) -> () -> {
			return this.field == field && s.length() == i2;
		};
	}
	
	public static void main(String[] arg) {
		Test t = new Test();
		A a = t.getA();
		testTrue("Lambda", a.m(3).m("yes", 3).m());
		t.field = 2;
		testTrue("Lambda", !a.m(3).m("yes", 3).m());
	}
}
