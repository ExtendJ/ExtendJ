// Definite assignment of resource variables.
// .result=COMPILE_PASS
public class Test {
  void pass() {
    try (java.io.PrintStream r = System.out) {
      r.println();
    }
  }
}
