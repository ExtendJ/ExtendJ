// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;
import java.lang.*;

public class Test {
	int field = 2;
	
	public int getField() {
		return field;
	}
	
	public class InnerTest extends Test {
		int field = 3;
		public int getField() {
			return field;
		}
		
		public A getA() {
			return (int a) -> {
				return (String b) -> {
					return (double d) -> {
						return () -> super.field + (int)d + b.length() + a;
					};
				};
			};
		}
	}
	public interface A {
		public B m(int a);
	}
	public interface B {
		public C m(String b);
	}
	public interface C {
		public D m(double d);
	}
	public interface D {
		public int m();
	}
	
	public static void main(String[] arg) {
		InnerTest t = new Test().new InnerTest();
		A a = t.getA();
		testTrue("Lambda", a.m(2).m("yeah").m(4.5).m() == 12);
	}
}
