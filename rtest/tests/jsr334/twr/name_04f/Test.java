// Within the try block, a resource name may not be rededeclared as a local
// variable of the directly enclosing method or initializer.
// .result=COMPILE_FAIL
public class Test {
  void fail() {
    try (java.io.PrintStream r = System.out) {
      Object r;
    }
  }
}
