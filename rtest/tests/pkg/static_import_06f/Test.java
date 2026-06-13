// It is not allowed to static-import a package-private type from another package.
// https://bitbucket.org/extendj/extendj/issues/289/illegal-static-type-import-is-allowed
// .result: COMPILE_FAIL
import static my.thing.A.Thing; // Error: illegal import.

public class Test { }
