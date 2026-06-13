// .result=COMPILE_PASS
import java.io.IOException;
import java.util.*;

public class Test {

	interface A {
		int m() throws IOException;
	}
	
	static int method() throws IOException {
		return 5;
	}
	
	public static void main(String[] arg) {
		A a = Test::method;
	}
}