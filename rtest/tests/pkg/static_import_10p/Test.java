// It is allowed to static-import a protected type from the same package.
// .result: COMPILE_PASS
package my.thing;

import static my.thing.A.Thing; // OK: same package.

public class Test { }
