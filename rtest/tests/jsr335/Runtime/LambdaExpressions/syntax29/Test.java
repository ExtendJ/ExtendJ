// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.io.IOException;
import java.util.*;

public class Test {
	
	public interface A {
		void m(int param);
	}
	
	public interface B { 
		void m();
	}

	static A a;
	// Test capturing another lambda
	public static A getA(B b1) {
		B b2 = () -> System.out.println("b2");
		a = p -> {
			if(p <= 0)
				return;
			a.m(p - 1);
			System.out.println(p + "");
			b1.m();
			b2.m();
		};
		return a;
	}
	
	public static void main(String[] arg) {
		A a = getA((B)() -> System.out.println("Is b1"));
		a.m(5);
	}
}
