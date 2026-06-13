// Single resource, no trailing semicolon.
// .result: COMPILE_PASS
public class Test {
  void pass() {
    try (java.io.PrintStream resource1 = System.out) {
    }
  }
}
