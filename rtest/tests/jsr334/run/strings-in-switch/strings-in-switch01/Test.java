// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

/**
 * Test that the default label works in
 * a switch with string expression.
 */
public class Test {
	public static void main(String[] args) {
		switch ("foo") {
			case "bar":
				break;
			default:
				return;
		}
		fail("Missed the correct case label!");
	}
}
