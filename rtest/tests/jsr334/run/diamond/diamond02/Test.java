// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

/**
 * Test type inference for a class implementing
 * an interface.
 */
public class Test {
	public static void main(String[] args) {
		java.util.List<Integer> list = new java.util.ArrayList<>();
		list.add(1);
		list.add(2);
		list.add(4);
		int sum = 0;
		for (int v : list)
			sum += v;
		testEqual("sum", 7, sum);
	}
}
