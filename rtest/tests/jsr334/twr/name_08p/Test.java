// A resource name may be used as the name of an exception parameter of
// one of the catch clauses of the try-with-resources statement.
// .result=COMPILE_PASS
public class Test {
  void pass() {
    try (AutoCloseable r = System.out) {
    } catch (Exception r) {
    }
  }
}
