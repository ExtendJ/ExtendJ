// Unreachable statement test.
// .result=COMPILE_FAIL
public class Test {
  void fail() {
    try (java.io.PrintStream r = System.out) {
      return;
    }
    new Integer(1).toString(); // Unreachable.
  }
}
