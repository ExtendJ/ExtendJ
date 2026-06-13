// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;
import java.lang.*;

public class Test {
	public interface A {
		public boolean m();
	}
	
	int a = 2;
	
	public A getA(int param) {
		return () -> param == this.a;
	}
	
	public static void main(String[] arg) {
		Test t = new Test();
		A a = t.getA(2);
		testTrue("Lambda", a.m());
		t.a = 4;
		testTrue("Lambda", !a.m());
	}
}
