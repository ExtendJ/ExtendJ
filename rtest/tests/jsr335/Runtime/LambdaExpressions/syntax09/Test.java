// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;
import java.lang.*;

public class Test {
	public interface A {
		public void print(String m);
	}
	
	public static void printMessage(String m) {
		System.out.println(m);
	}
	
	public static void main(String[] arg) {
		A a = m -> printMessage(m);
		a.print("this is the result");
	}
}
