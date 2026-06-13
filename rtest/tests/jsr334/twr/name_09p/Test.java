// A resource name may be used as the name of a variable in the finally block
// of a try-with-resources statement.
// .result=COMPILE_PASS
public class Test {
  void pass() {
    try (java.io.PrintStream r = System.out) {
    } finally {
      int r;
    }
  }
}
