// Final modifier in resource specification.
// .result=COMPILE_PASS
public class Test {
  void pass() {
    try (final java.io.PrintStream resource1 = System.out) {
    }
  }
}
