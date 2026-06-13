// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;
import java.lang.*;

public class Test {
	public interface A {
		public int[] m(int size);
	}
	
	public static void main(String[] arg) {
		String s = "len4";
		A a = s.length()==5 ? p -> new int[p] : p -> new int[p + 2];
		
		testTrue("Lambda", a.m(2).length == 4);
	}
}
