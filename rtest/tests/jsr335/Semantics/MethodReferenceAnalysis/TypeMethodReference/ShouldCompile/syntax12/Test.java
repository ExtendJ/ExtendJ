// .result=COMPILE_PASS
import java.util.*;

public class Test {
	
	interface A { 
		B method(C<B, String> c, B b, String s, ArrayList<Integer> l); 
	}
	
	interface B {
		int method(String s, List<Integer> l);
	}
	
	interface C<S, T> {
		default <Z> S method(S s, T t, Z z) {
			return null;
		}
	}
	
	public void testMethod() {
		A a = C::<List<Integer>>method;
	}
}