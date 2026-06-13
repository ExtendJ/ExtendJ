// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

/**
 * Test different kinds of string labels in a
 * switch with string expression.
 */
public class Test {
	public static void main(String[] args) {
		switch ("lbl"+2) {
			case "lbl1":
				break;
			case "lbl2":
				return;
		}
		fail("Missed the correct case label!");
	}
}
