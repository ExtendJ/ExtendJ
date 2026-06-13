// Tests exception checking for anonymous class instance expressions.
//
// Bitbucket ticket:
// https://bitbucket.org/jastadd/jastaddj/issue/10/exception-checking-error-for-anonymous
// .result=COMPILE_PASS
import java.io.IOException;

public class Test {
  class Foo {
    Foo() throws IOException {
      throw new IOException("oops");
    }
  }
  public void foo() {
    try {
      new Foo() { };
    } catch (IOException e) {
    }
  }
}
