// .result=COMPILE_FAIL
import java.io.EOFException;
import java.io.IOException;


public class Test {
	
	interface A {
		int m(int i) throws EOFException;
	}
	
	public int method(int i) throws IOException {
		return i;
	}
	
	
	
	public int m1(double d, String s) {
		A a = this::method;
		return 4;
	}
}