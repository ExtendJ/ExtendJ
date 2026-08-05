// Test that the Unknown type is not mentioned in an error message.
// See issue 249.
// .result: COMPILE_FAIL
public class Test {
  Test fail() {
    return oh_hi_mark;
  }
}
