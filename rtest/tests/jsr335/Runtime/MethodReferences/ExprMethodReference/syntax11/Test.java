// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

public class Test {
	public interface A {
		int m(int s, long d);
	}
	
	public <S extends Number, T extends Number> int method(S s, T t) {
		return s.intValue() + t.intValue();
	}
	
	public static void main(String[] arg) {
		Test t = new Test();
		A a = t::<Integer, Long>method;
		testTrue("MethodRef", a.m(3, 5) == 8);
	}
}
