// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.io.IOException;
import java.util.*;

public class Test {

	public interface A<T> {
		B m(T t);
	}
	
	public class B {
		int i = 0;
		public B(int i) {
			this.i = i;
		}
		public B(long i) {
			this.i = (int)i + 1;
		}
		public B(String s) {
			this.i = 100;
		}
	}
	
	public void testMethod() {
		A<Integer> a1 = B::new;
		A<Long> a2 = B::new;
		A<String> a3 = B::new;
		
		testTrue("ConstructorReference", a1.m(1).i == 1);
		testTrue("ConstructorReference", a2.m((long)1).i == 2);
		testTrue("ConstructorReference", a3.m("yes").i == 100);
	}
	
	public static void main(String[] arg) {
		new Test().testMethod();
	}
}
