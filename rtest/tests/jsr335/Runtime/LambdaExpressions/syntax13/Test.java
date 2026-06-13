// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;
import java.lang.*;

public class Test {
	public interface A {
		public int m(int p);
	}
	
	static int outerInt = 4;
	
	public static A getA(boolean cond) {
		int local2;
		int local = cond ? (local2 = 5) + 2 : (local2 = 9) + 1;
		return p -> {
			int result = 0;
			if(cond)
				result++;
			if(outerInt > p)
				result += local2;
			else
				result += local;
			result += local - local2;
			return result;
		};
	}
	
	public static void main(String[] arg) {
		A a = getA(true);
		testTrue("Lambda", a.m(3) == 8);
		outerInt = 2;
		testTrue("Lambda", a.m(3) == 10);
	}
}
