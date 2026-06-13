// @SafeVarargs is not allowed on a non-static, non-final method.
// https://bitbucket.org/extendj/extendj/issues/208/safevarargs-annotation-on-private-methods
// .result: COMPILE_FAIL
public class Test {
  @SafeVarargs private void fail(Float... f) { }
}
