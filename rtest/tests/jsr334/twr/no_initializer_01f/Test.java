// Resource declarations must have initializer expression.
// See issue 209.
// .result: COMPILE_FAIL
public class Test {
  void fail() {
    try (AutoCloseable r) {
      r = System.out;
    } catch (Exception r) {
    }
  }
}
