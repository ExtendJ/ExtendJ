// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

/**
 * Test different kinds of string labels in a
 * switch with string expression.
 */
public class Test {
	public static void main(String[] args) {
		String value = "foobar";
		switch (value) {
			case "foo":
			case "bar":
				break;
			case "foo"+"bar":
				return;
		}
		fail("Missed the correct case label!");
	}
}
