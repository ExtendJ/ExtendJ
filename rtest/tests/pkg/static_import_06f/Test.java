// It is not allowed to static-import a package-private type from another package.
// See issue 289.
// .result: COMPILE_FAIL
import static my.thing.A.Thing; // Error: illegal import.

public class Test { }
