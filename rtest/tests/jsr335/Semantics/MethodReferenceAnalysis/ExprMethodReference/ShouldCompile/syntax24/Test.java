// .result=COMPILE_PASS
import java.io.EOFException;
import java.io.IOException;
import java.util.*;


public class Test {
	
	interface A {
		void m(int i);
	}
	
	public void testMethod() {
		A a = this::m1;
	}
	
	public int m1(double a) {
		return 3;
	}
	
	public boolean m1(int a) {
		return true;
	}
	
	public String m1(long a) {
		return "yes";
	}
	
	public int m1(Object o) {
		return 3;
	}
}