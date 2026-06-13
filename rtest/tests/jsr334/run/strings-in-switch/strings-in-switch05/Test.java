// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

/**
 * Test different kinds of string labels in a
 * switch with string expression.
 */
public class Test {
	public static void main(String[] args) {
		String value = "13";
		switch (value) {
			case " 13":
			case "13 ":
				break;
			case ""+"13":
				return;
		}
		fail("Missed the correct case label!");
	}
}
