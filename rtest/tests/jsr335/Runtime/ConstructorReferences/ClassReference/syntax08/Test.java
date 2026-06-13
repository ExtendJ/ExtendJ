// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.io.IOException;
import java.util.*;

public class Test {

	public interface A {
		<T extends LinkedList<Integer>> B m(T t);
	}
	
	public class B {
		int i = 0;
		public B(List<Integer> i) {
			this.i = 1;
		}
		public B(Collection<Integer> i) {
			this.i = 2;
		}
	}
	
	public void testMethod() {
		A a = B::new;
		testTrue("ConstructorReference", a.m(null).i == 1);
	}
	
	public static void main(String[] arg) {
		new Test().testMethod();
	}
}
