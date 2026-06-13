// The name of a resource may be shadowed anywhere inside a class declaration
// nested within the block of a try statement.
// .result=COMPILE_PASS
public class Test {
  void pass() {
    try (java.io.PrintStream r = System.out) {
      new Object() {
        public void foo() {
          int r;
        }
      };
    }
  }
}
