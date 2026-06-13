// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;
import java.lang.*;

public class Test {
	public interface A {
		public void print(String m);
	}
	
	
	
	public static void main(String[] arg) {
		A a = m -> System.out.println(m);
		a.print("output");
	}
}
