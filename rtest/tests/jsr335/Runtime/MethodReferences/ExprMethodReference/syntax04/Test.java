// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

public class Test {
	long i = 0;
	
	public interface A {
		double m();
	}
	public interface B {
		void m(int i);
	}
	
	public void testMethod() {
		A a = this::getI;
		B b = this::setI;
		b.m(10);
		testTrue("MethodRef", a.m() > 9);
	}
	
	public void setI(long i) {
		this.i = i;
	}
	
	public long getI() {
		return this.i;
	}
	
	public static void main(String[] arg) {
		Test t = new Test();
		t.testMethod();
	}
}
