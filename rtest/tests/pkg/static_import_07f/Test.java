// It is not allowed to static-import a non-static type.
// .result: COMPILE_FAIL
import static my.thing.A.Thing; // Error: illegal import.

public class Test { }
