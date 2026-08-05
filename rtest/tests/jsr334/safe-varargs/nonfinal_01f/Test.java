// @SafeVarargs is not allowed on a non-static, non-final method.
// See issue 208.
// .result: COMPILE_FAIL
public class Test {
  @SafeVarargs private void fail(Float... f) { }
}
