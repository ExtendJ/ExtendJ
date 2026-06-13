// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;
import java.lang.*;

public class Test {
	int field = 2;
	public interface A {
		public B m();
	}
	public interface B {
		public int m(int field);
	}
	
	public A getA() {
		return () -> {
			B b = new B() {
				int field = 3;
				public int m(int field) {
					return this.field;
				}
			};
			if(this.field == 2)
				return b;
			else
				return field -> field;
		};
	}
	
	public static void main(String[] arg) {
		Test t = new Test();
		A a = t.getA();
		B b = a.m();
		testTrue("Lambda", b.m(100) == 3);
		testTrue("Lambda", b.m(1000) == 3);
		t.field = 3;
		b = a.m();
		testTrue("Lambda", b.m(100) == 100);
		testTrue("Lambda", b.m(1000) == 1000);
	}
}
