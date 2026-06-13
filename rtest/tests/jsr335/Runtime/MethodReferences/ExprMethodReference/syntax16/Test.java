// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

public class Test {
	public interface A<T> {
		B<String> m(int i, T s);
	}
	public interface B<T> {
		C<Integer> m(T s);
	}
	public interface C<T> {
		T m();
	}
	
	public static void main(String[] arg) {
		int local = 5;
		A<String> a = (i, s1) -> {
			if(local > i)
				return s2 -> {
					return s2::length;
				};
			else
				return s2 -> s1::length;
		};
		
		B<String> b1 = a.m(4, "yes");
		B<String> b2 = a.m(6, "methodRef");
		C<Integer> c1 = b1.m("testing");
		C<Integer> c2 = b2.m("testing");
		testTrue("MethodRef", c1.m() == 7);
		testTrue("MethodRef", c2.m() == 9);
	}
}
