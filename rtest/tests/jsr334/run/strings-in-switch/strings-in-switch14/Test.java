// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

/**
 * <p>This is one of four test cases to test the two basic
 * types of code generation which can happen for switch
 * statements.
 *
 * <p>A switch statement may be translated to bytecode using
 * either the lookupswitch or tableswitch instructions.
 * We should test that byte code was generated correctly for
 * both cases.
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
			case "smurf":
				break;
		}
		fail("Missed the correct case label!");
	}
}
