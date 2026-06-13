// Must catch unhandled exception on close.
// .result=COMPILE_FAIL
public class Test {
  void fail() {
    try (AutoCloseable foo = System.out) {
    }
  }
}
