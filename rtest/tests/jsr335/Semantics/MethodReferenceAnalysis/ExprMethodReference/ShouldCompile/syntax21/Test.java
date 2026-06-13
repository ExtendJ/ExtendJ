// .result=COMPILE_PASS
import java.io.EOFException;
import java.io.IOException;
import java.util.*;


public class Test {
	
	interface A {
		void m(int a, float f);
	}
	
	public void testMethod() {
		A a = this::m1;
		a = this::m2;
	}
	
	public int m1(int a, float f) {
		return a;
	}
	public ArrayList<Integer> m2(long a, double f) {
		return null;
	}
	
}