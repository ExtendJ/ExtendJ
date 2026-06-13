// .result=COMPILE_PASS
import java.io.EOFException;
import java.io.IOException;
import java.util.*;


public class Test {
	
	interface A<T, S> {
		void m(T t, S s);
	}
	
	public void testMethod() {
		A a = this::m1;
	}
	
	public int m1(Object a, Object s) {
		return 3;
	}
}