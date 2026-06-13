// Test that the Unknown type is not mentioned in an error message.
// https://bitbucket.org/extendj/extendj/issues/249/clean-up-unknown-in-error-messages
// .result: COMPILE_FAIL
public class Test {
  Test fail() {
    return oh_hi_mark;
  }
}
