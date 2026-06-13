// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

public class Test {
	public interface A {
		void m(int i);
	}
	public interface B {
		void m(int i);
	}
	public interface C {
		void m(int i);
	}
	
	public static void main(String[] arg) {
		Test t = new Test();
		A a = i -> { };
		B b = a::m;
		C c = b::m;
		if(!(a instanceof A))
			fail("Error: expected instance of A");
		if(!(b instanceof B))
			fail("Error: expected instance of B");
		if(!(c instanceof C))
			fail("Error: expected isntance of C");
	}
}
