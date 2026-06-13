// Test that Unknown type is not referred to in error message.
// https://bitbucket.org/extendj/extendj/issues/249/clean-up-unknown-in-error-messages
// .result: COMPILE_FAIL
public class Test {
  void fail() {
    int x = missing();
  }
}
