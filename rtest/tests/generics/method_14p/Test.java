// Test type inference for simple generic method access.
// https://bitbucket.org/extendj/extendj/issues/198/generic-method-type-inference-failure-in
// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

public class Test {
  <T extends Number> T num() {
    return null;
  }

  String numStr() {
    return "num: " + num();
  }

  public static void main(String[] args) {
    Test test = new Test();
    testEquals("num: null", test.numStr());
  }
}
