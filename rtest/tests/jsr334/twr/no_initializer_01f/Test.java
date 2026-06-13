// Resource declarations must have initializer expression.
// https://bitbucket.org/extendj/extendj/issues/209/try-with-resources
// .result: COMPILE_FAIL
public class Test {
  void fail() {
    try (AutoCloseable r) {
      r = System.out;
    } catch (Exception r) {
    }
  }
}
