// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

public class Test {

	public interface A {
		Inner m(Integer i);
	}
	public interface B {
		Inner m(int i);
	}
	public interface C {
		Inner m(long i);
	}
	public interface D {
		Inner m(String s);
	}
	public interface E {
		Inner m(ArrayList<Integer> list);
	}
	public interface F {
		Inner m(LinkedList<String> list);
	}
	
	public class Inner {
		int i = 0;
		
		public <T> Inner(T t) {
			if(t instanceof List)
				this.i = 100;
			else
				this.i = 50;
		}
		public Inner(Integer i) {
			this.i = i.intValue();
		}
	}
	
	public void testMethod() {
		A a = Inner::new;
		testTrue("ConstructorReference", a.m(2).i == 2);
		a = Inner::<Integer>new;
		testTrue("ConstructorReference", a.m(4).i == 4);
		B b = Inner::new;
		testTrue("ConstructorReference", b.m(3).i == 3);
		C c = Inner::new;
		testTrue("ConstructorReference", c.m(2).i == 50);
		D d = Inner::<String>new;
		testTrue("ConstructorReference", d.m("yes").i == 50);
		E e = Inner::<List<Integer>>new;
		testTrue("ConstructorReference", e.m(new ArrayList<Integer>()).i == 100);
		F f = Inner::new;
		testTrue("ConstructorReference", f.m(new LinkedList<String>()).i == 100);
	}
	
	public static void main(String[] arg) {
		new Test().testMethod();
	}
}
