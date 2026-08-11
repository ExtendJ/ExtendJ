// Same as throws_bound_01p, but the type parameter is bounded by a checked exception type.
// .result: COMPILE_FAIL
import java.io.IOException;

public class Test {
  static <Pam extends IOException> void bird(Bird<Pam> block) throws Pam {
    block.execute();
  }

  void test() {
    // The lambda body throws only unchecked exceptions but because
    // the bound on Pam is IOException the call to bird will require
    // catching the checked exception type IOException.
    bird(() -> { throw new RuntimeException(); }); // Error: IOException not caught or declared thrown.
  }
}

interface Bird<B extends IOException> {
  void execute() throws B;
}
