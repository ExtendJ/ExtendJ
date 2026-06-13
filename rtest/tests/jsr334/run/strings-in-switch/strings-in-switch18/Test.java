// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

/**
 * Test that the switch expression is only evaluated once,
 * even with a hash collision.
 */
public class Test {
	public static void main(String[] args) {
		List<String> list = new LinkedList<String>();
		list.add("FB");
		list.add("Ea");
		Iterator<String> iter = list.iterator();
		switch (iter.next()) {
			default:
		}
		testEqual("side effects evaluated twice", true, iter.hasNext());
	}
}
