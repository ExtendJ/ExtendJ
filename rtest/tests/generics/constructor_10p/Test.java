// Test that ExtendJ correctly parses generic constructor bytecode.
// See issue 274.
// .result: COMPILE_PASS
// .classpath: @RUNTIME_CLASSES@
import runtime.GenericConstructor;

public class Test {
  void pass() {
    new GenericConstructor(this);
  }
}
