// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;
import java.lang.*;

public class Test {
	public interface A {
		void m(int a);
	}
	
	static A a;
	public static void main(String[] arg) {
		a = input -> {
			if(input <= 0)
				return;
			else 
				a.m(input - 1);
			System.out.println("" + input);
		};
		a.m(10);
	}
}
