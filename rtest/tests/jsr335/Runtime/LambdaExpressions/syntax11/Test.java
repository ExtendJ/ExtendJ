// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;
import java.lang.*;

public class Test {
	public interface A {
		public int m(String s);
	}
	
	public static void main(String[] arg) {
		A[] as = {s -> s.length(), s -> s.length() + 1, (String s) -> s.length() + 2};
		String s = "len4";
		int result = 0;
		for(int i = 0; i < as.length; i++) {
			result += as[i].m(s);
		}
		testTrue("Lambda", result == 15);
	}
}
