// Within the try block, a resource name may not be rededeclared as an
// exception parameter of a try statement of the directly enclosing
// method or initializer block.
// .result=COMPILE_FAIL
public class Test {
  void fail() {
    try (java.io.PrintStream r = System.out) {
      try {
        throw new Exception();
      } catch (Exception r) {
      }
    }
  }
}
