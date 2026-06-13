// Can not post-decrement a final variable.
// .result: COMPILE_FAIL
public class Test {
  void fail(final int n) {
    n++;
  }
}
