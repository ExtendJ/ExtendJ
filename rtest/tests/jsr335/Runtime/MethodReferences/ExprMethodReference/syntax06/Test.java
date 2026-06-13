// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

public class Test {
	int i = 2;
	
	public interface A {
		int m();
	}
	
	public class Inner extends Test {
		int i = 3;
		public int getI() {
			return this.i;
		}
		
		public void testMethod() {
			A a1 = this::getI;
			A a2 = super::getI;
			A a3 = Inner.super::getI;
			testTrue("MethodRef", a1.m() == 3);
			testTrue("MethodRef", a2.m() == a3.m() && a2.m() == 2);
		}

	}
	

	
	public int getI() {
		return this.i;
	}
	
	public static void main(String[] arg) {
		Test t = new Test();
		Inner in = t.new Inner();
		in.testMethod();
	}
}
