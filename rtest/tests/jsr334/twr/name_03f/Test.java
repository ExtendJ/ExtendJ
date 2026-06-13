// The resource may not have the same name as a local variable of the method
// immediately enclosing the try statement.
// .result=COMPILE_FAIL
public class Test {
  void fail() {
    Object r;
    try (java.io.PrintStream r = System.out) {
    }
  }
}
