// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

/**
 * Test different kinds of string labels in a
 * switch with string expression.
 */
public class Test {
	public static void main(String[] args) {
		String value = "foo";
		switch (value) {
			case "bar":
			case "foo":
				return;
			case "furburr":
				break;
		}
		fail("Missed the correct case label!");
	}
}
