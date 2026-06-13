// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

/**
 * Test nested switch statements with string expressions.
 */
public class Test {
	public static void main(String[] args) {
		String expr = "foo";
		switch (expr) {
			case "bar":
				break;
			case "foo":
				switch (expr+2) {
					case "foo1":
						break;
					case "foo2":
						return;
					case "foo3":
					case "moo":
					default:
						break;
				}
				break;
			case "cat":
				break;
			default:
				break;
		}
		fail("Missed the correct case label!");
	}
}
