// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

/**
 * @see testLookupSwitchNoDefault
 */
public class Test {
	public static void main(String[] args) {
		String	value = "b";
		switch (value) {
			case "a":
				break;
			case "b":
				return;
			case "c":
				break;
			case "d":
				break;
		}
		fail("Missed the correct case label!");
	}
}
