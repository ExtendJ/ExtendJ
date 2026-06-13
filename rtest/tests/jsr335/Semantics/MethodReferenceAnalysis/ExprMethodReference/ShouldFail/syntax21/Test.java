// .result=COMPILE_FAIL
import java.io.EOFException;
import java.io.IOException;


public class Test {

	interface A {
		String m();
	}
	
	public static String method() {
		return "string";
	}
	
	public void testMethod() {
		A a = this::method;
	}

}