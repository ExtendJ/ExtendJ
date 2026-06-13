// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

public class Test {
	public interface A {
		B m(int i, String s);
	}
	public interface B {
		C m(String s);
	}
	public interface C {
		int m();
	}
	
	public static void main(String[] arg) {
		int local = 5;
		A a = (i, s1) -> {
			if(local > i)
				return s2 -> {
					return s2::length;
				};
			else
				return s2 -> s1::length;
		};
		
		B b1 = a.m(4, "yes");
		B b2 = a.m(6, "methodRef");
		C c1 = b1.m("testing");
		C c2 = b2.m("testing");
		testTrue("MethodRef", c1.m() == 7);
		testTrue("MethodRef", c2.m() == 9);
	}
}
