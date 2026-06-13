// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;
import java.lang.*;

public class Test {
	static int outerInt = 3;
	
	public static void main(String[] arg) throws InterruptedException {
		Thread t = new Thread((Runnable)() -> {
			int start = 0;
			while(start < 10) {
				outerInt++;
				start++;
			}
		});

		t.start();
		t.join();
		testTrue("Lambda", outerInt == 13);
	}
}
