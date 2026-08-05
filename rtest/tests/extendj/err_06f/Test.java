// Test that Unknown type is not referred to in error message.
// See issue 249.
// .result: COMPILE_FAIL
public class Test {
  void fail() {
    int x = missing();
  }
}
