// Unreachable statement.
// .result=COMPILE_FAIL
public class Test {
  void fail() {
    try (java.io.PrintStream r = System.out) {
      throw new Exception();
    } catch (Exception e) {
      return;
    }
    // Unreachable:
    new Integer(1).toString();
  }
}
