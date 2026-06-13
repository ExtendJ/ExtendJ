// .result=COMPILE_PASS
import java.io.EOFException;
import java.io.IOException;
import java.util.*;


public class Test {
	
	interface A<T, S> {
		void m(T t, S s);
	}
	
	public void testMethod() {
		A<Integer, String> a = this::m1;
	}
	
	public int m1(int a, String s) {
		return 3;
	}
}