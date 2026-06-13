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
			return () -> super.getField();
		}
	}
	public interface A {
		public int m();
	}
	
	public static void main(String[] arg) {
		InnerTest t = new Test().new InnerTest();
		A a = t.getA();
		testTrue("Lambda", a.m() == 2);
	}
}
