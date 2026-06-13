// .result=COMPILE_PASS
import java.io.IOException;
import java.util.*;

public class Test  {

	interface A {
		int m(B<Integer> b, int i);
	}
	
	class B<T extends Number> {
		public int method(T t) {
			return t.intValue() + 4;
		}
	}
	
	public void testMethod() {
		A a1 = B::method;
		A a2 = B<Integer>::method;
	}
}