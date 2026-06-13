// .result=COMPILE_FAIL
import java.io.EOFException;
import java.io.IOException;


public class Test {
	public interface A {
		int m(String s);
	}
	
	public <T> int method(String s) {
		return 2;
	}
	
	
	public static void main(String[] arg) {
		Test t = new Test();
		A a = t::<? extends String>method;
	}
}