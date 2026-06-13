// This should fail to compile because the type B is not imported!
// https://bitbucket.org/extendj/extendj/issues/287/static-import-declaration-imports-too-much
// .result: COMPILE_FAIL
import static pkg.A.newB;

public class Test {
  B fail = newB(); // Error: B not imported!
}
