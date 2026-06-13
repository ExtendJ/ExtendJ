// Must catch unhandled exception on close.
// .result=COMPILE_PASS
public class Test {
  void pass() {
    try (AutoCloseable foo = System.out) {
    } catch (Exception e) {
      // Handle possible exception from close.
    }
  }
}
