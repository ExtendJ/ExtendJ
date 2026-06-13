// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.io.*;

public class Test {
	static class E1 extends Exception {}
	static class E2 extends Exception {}
	public static void main(String[] args) {
		try {
			int a = 0;
			if (a < 0) new FileInputStream("not-a-file");
		} catch (IOException | SecurityException e) {
			e.printStackTrace();
			fail("no exception should be caught");
		}
	}
}
