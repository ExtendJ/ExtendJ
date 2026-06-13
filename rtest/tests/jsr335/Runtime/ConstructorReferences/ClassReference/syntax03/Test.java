// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;
import java.util.*;

public class Test {
	public interface A {
		Inner m(int i);
	}
	public interface B {
		Inner m(short i);
	}
	public interface C {
		Inner m(long i);
	}
	public interface D {
		Inner m(float i);
	}
	public interface E {
		Inner m(double i);
	}
	public interface F {
		Inner m(String i);
	}
	
	public class Inner {
		int i = 0;
		public Inner(int i) {
			this.i = i;
		}
		public Inner(short i) {
			this.i = (int)i + 1;
		}
		public Inner(long i) {
			this.i = (int)i + 2;
		}
		public Inner(float i) {
			this.i = (int)i + 3;
		}
		public Inner(double i) {
			this.i = (int)i + 4;
		}
		public Inner(String i) {
			this.i = 100;
		}
	}
	
	public void testMethod() {
		A a = Inner::new;
		B b = Inner::new;
		C c = Inner::new;
		D d = Inner::new;
		E e = Inner::new;
		F f = Inner::new;
		
		testTrue("MethodReference", a.m(1).i == 1);
		testTrue("MethodReference", b.m((short)1).i == 2);
		testTrue("MethodReference", c.m(1).i == 3);
		testTrue("MethodReference", d.m(1).i == 4);
		testTrue("MethodReference", e.m(1).i == 5);
		testTrue("MethodReference", f.m("yes").i == 100);
	}
	
	public static void main(String[] arg) {
		Test t = new Test();
		t.testMethod();
	}
}
