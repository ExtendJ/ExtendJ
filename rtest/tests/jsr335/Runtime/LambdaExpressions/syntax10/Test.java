// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;
import java.lang.*;

public class Test {
	public interface A {
		public <T extends Integer> int m(T t);
	}
	public interface B {
		public int m(Integer i);
	}
	public interface C extends A, B { }
	
	public static void main(String[] arg) {
		C c = (Integer i) -> i.intValue() + 5;
		testTrue("Lambda", c.m(5) == 10);
	}
}
