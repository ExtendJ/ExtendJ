// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;
import java.lang.*;

public class Test {
	public interface A<T> {
		B<T> m();
	}
	public interface B<T> {
		A<T> m(T t);
	}
	
	static int tmp = 0;
	static A<Integer> a;
	public static void main(String[] arg) {
		a = () -> {
			return p1 -> () -> p2 -> () -> p3 -> () -> p4 -> () -> {
				tmp = p1 + p2 + p3 + p4;
				return a.m();
			};
		};
		
		B<Integer> b = a.m().m(1).m().m(2).m().m(3).m().m(4).m();
		testTrue("Lambda", tmp == 10);
	}
}
