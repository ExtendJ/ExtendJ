// The resource declaration type must implement java.lang.AutoCloseable.
// .result=COMPILE_FAIL
public class Test {
  void fail() {
    try (Object foo = System.out) {
    }
  }
}
