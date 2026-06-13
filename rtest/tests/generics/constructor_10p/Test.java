// Test that ExtendJ correctly parses generic constructor bytecode.
// https://bitbucket.org/extendj/extendj/issues/274/failure-to-parse-generic-constructor
// .result: COMPILE_PASS
// .classpath: @RUNTIME_CLASSES@
import runtime.GenericConstructor;

public class Test {
  void pass() {
    new GenericConstructor(this);
  }
}
