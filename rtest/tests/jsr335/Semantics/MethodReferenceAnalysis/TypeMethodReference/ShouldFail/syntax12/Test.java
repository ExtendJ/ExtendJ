// .result=COMPILE_FAIL
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Test {

	interface A {
		int m();
	}
	
	static int method() throws IOException {
		return 5;
	}
	
	public static void main(String[] arg) {
		A a = Test::method;
	}
}