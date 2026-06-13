// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;
import java.lang.*;

public class Test {

	public interface A {
		public Test m();
	}
	
	public A getA() {
		return () -> this;
	}
	
	public static void main(String[] arg) {
		Test t = new Test();
		A a = t.getA();
		testSame("Lambda", t, a.m());
	}
}
