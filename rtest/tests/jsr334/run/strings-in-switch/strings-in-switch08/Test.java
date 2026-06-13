// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

/**
 * Test case labels with same hash code.
 */
public class Test {
	public static void main(String[] args) {
		String str = "fun";// 0x00018D7F
		switch (str) {
			case "h90":
				break;
			case "fvO":
				break;
			case "gVn":
				break;
			case "fun":
				return;
			case "gWO":
				break;
			case "gX0":
				break;
			case "h7n":
				break;
			case "h80":
				break;
		}
		fail("Missed the correct case label!");
	}
}
