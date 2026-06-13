// It is allowed to static-import a package-private type from the same package.
// https://bitbucket.org/extendj/extendj/issues/295/static-import-of-package-private-type-from
// .result: COMPILE_PASS
package my.thing;

import static my.thing.A.Thing; // OK: same package.

public class Test { }
