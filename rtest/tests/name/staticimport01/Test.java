// Tests a bug in name analysis that incorrectly reports several most specific
// methods.
// Bitbucket issue: https://bitbucket.org/jastadd/jastaddj/issue/8/incorrect-several-most-specific-methods
// .result=COMPILE_PASS
import static a.A.fail;
import static b.B.notfail;

public class Test {
  void m() {
    fail();
  }
}
