// The name of a resource may be shadowed anywhere inside a class declaration
// nested within the block of a try statement.
// .result=COMPILE_PASS
public class Test {
  void pass() {
    try (java.io.PrintStream r1 = System.out) {
      try (AutoCloseable r2 = new AutoCloseable() {
            public void close() {
              int r1, r2;
            }
          }) {
      } catch (Exception e) {
      }
    }
  }
}
