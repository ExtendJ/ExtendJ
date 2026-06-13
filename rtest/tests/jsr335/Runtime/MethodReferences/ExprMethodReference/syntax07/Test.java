// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

public class Test {
	int i = 2;
	
	public interface A {
		long m(int a, long b, short c, double d, float f);
	}
	
	public int m(int a, long b, short c, double d, float f) {
		return 1;
	}
	
	public long m(int a, long b, short c, double d, double f) {
		return 2;
	}
	
	public int m(long a, long b, short c, double d, float f) {
		return 3;
	}
	
	public long m(int a, double b, short c, double d, float f) {
		return 4;
	}
	
	public int m(int a, long b, int c, double d, float f) {
		return 5;
	}
	
	public static void main(String[] arg) {
		Test t = new Test();
		A a = t::m;
		testTrue("MethodRef", a.m(2, 4, (short)5, 4, 2) == 1);
	}
}
