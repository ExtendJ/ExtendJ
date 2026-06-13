// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

/**
 * Test fall-through into a hash-collision label.
 */
public class Test {
	public static void main(String[] args) {
		int count = 0;
		switch ("cat") {
			case "dog":
				count = -100;
			case "cat":
				count += 1;
			case "FB":
				count += 2;
			case "Ea":
				count += 4;
		}
		testEqual("Fall-through error!", 7, count);
	}
}
