// .result=COMPILE_PASS
import java.io.EOFException;
import java.io.IOException;
import java.util.*;


public class Test {
	public interface A {
		int m();
	}
	
	public interface B {
		default int m() {
			return 5;
		}
	}

	public class Inner extends Test implements B {

		public int m() {
			return 3;
		}
		
		public void testMethod() {
			A a = B.super::m;
		}
		
	}
}