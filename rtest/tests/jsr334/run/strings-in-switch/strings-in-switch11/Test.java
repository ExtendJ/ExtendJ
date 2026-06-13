// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

/**
 * Test fall-through in labels with same hash code.
 */
public class Test {
	public static void main(String[] args) {
		int count = 0;
		switch ("FB") {
			case "FB":
				count += 1;
			case "Ea":
				count += 2;
		}
		testEqual("Fall-through error!", 3, count);
	}
}
