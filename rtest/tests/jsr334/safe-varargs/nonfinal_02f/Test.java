// @SafeVarargs is not allowed on a non-static, non-final method.
// See issue 208.
// .result: COMPILE_FAIL
public final class Test {
  @SafeVarargs void fail(Float... f) { }
}
