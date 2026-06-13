// Test that the $assertionsDisabled field is treated as synthetic when parsed
// from bytecode.
// https://bitbucket.org/extendj/extendj/issues/297/synthetic-fields-parsed-from-classfile-are
// .result: COMPILE_FAIL
// .classpath=@RUNTIME_CLASSES@
import runtime.AssertionsDisabled;

public class Test {
  boolean b = AssertionsDisabled.$assertionsDisabled;
}
