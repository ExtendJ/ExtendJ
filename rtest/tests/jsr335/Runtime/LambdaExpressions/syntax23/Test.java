// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;
import java.lang.*;

public class Test {
	public interface A {
		A m();
	}
	
	static A a;
	public static void main(String[] arg) {
		// This test just checks that a is not a recursive lambda by seeing if execution can finish.
		a = () -> () -> a.m();
	}
}
