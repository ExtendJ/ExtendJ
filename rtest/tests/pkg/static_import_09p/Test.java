// It is allowed to static-import a package-private type from the same package.
// See issue 295.
// .result: COMPILE_PASS
package my.thing;

import static my.thing.A.Thing; // OK: same package.

public class Test { }
