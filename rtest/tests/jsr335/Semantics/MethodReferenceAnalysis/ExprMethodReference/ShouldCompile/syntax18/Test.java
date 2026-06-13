// .result=COMPILE_PASS
import java.io.EOFException;
import java.io.IOException;


public class Test {
	
	interface A {
		int m(int a, String s) throws IOException;
	}
	
	public void testMethod() {
		A a = this::method;
	}
	
	public int method(int a, String s) throws EOFException {
		return a + s.length();
	}
}