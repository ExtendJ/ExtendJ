// .result=COMPILE_FAIL
import java.io.EOFException;
import java.io.IOException;


public class Test {
	public interface A {
		int m(String s);
	}
	public interface B {
		int m(String s);
	}
	
	
	public static void main(String[] arg) {
		Test t = new Test();
		A a = (s -> s.length())::m;
	}
}