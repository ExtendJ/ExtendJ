// .result=COMPILE_FAIL
import java.io.EOFException;
import java.io.IOException;


public class Test {
	
	/*
	 * This test case currently fails, the reason is probably a bug in JastAddJ.
	 * See issue #73 in the git bitbucket repository
	 */
	
	interface A {
		String m();
	}
	
	public <T extends Number> T method() {
		return null;
	}
	
	public void testMethod() {
		A a = this::method;
	}

}