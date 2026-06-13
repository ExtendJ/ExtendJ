// .result=COMPILE_FAIL
import java.io.EOFException;
import java.io.IOException;


public class Test {
	public interface A {
		int m();
	}
	
	public interface B {
		default int m() {
			return 2;
		}
	}
	
	public interface C extends B {

	}
	
	public interface D extends B {
		default int m() {
			return 9;
		}
	}

	public class Inner extends Test implements C, D {

		public int m() {
			return 3;
		}
		
		public void testMethod() {
			A a = C.super::m;
		}
		
	}
}