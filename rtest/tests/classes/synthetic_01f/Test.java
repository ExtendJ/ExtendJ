// Test that the $assertionsDisabled field is treated as synthetic when parsed
// from bytecode.
// See issue 297.
// .result: COMPILE_FAIL
// .classpath=@RUNTIME_CLASSES@
import runtime.AssertionsDisabled;

public class Test {
  boolean b = AssertionsDisabled.$assertionsDisabled;
}
