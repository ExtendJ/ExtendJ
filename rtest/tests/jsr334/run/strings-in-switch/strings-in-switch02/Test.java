// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

/**
 * Test that a string label works in a switch
 * with string expression.
 */
public class Test {
	public static void main(String[] args) {
		String value = "some string";
		switch (value) {
			case "some string":
				return;
		}
		fail("Missed the correct case label!");
	}
}
