// This should fail to compile because the type B is not imported!
// See issue 287.
// .result: COMPILE_FAIL
import static pkg.A.newB;

public class Test {
  B fail = newB(); // Error: B not imported!
}
