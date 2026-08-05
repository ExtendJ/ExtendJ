// Tests a bug in name analysis that incorrectly reports several most specific
// methods.
// See issue 8.
// .result=COMPILE_PASS
import static a.A.fail;
import static b.B.notfail;

public class Test {
  void m() {
    fail();
  }
}
