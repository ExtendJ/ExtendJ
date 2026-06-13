// It is not allowed to static-import a private type from the same package.
// .result: COMPILE_FAIL
package my.thing;

import static my.thing.A.Thing; // Error: Thing is private.

public class Test { }
