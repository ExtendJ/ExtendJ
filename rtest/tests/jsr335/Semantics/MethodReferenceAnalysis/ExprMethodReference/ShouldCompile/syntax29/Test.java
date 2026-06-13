// .result=COMPILE_PASS
import java.io.EOFException;
import java.io.IOException;
import java.util.*;


public class Test {
	
	interface A {
		String m();
	}
	
	public <T> T method() {
		return null;
	}
	
	public void testMethod() {
		A a = this::method;
	}

}