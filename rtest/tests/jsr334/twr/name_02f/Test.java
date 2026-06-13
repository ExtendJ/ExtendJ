// The resource may not have the same name as a parameter of the method
// immediately enclosing the try statement.
// .result=COMPILE_FAIL
public class Test {
  public void fail(Object r) {
    try (java.io.PrintStream r = System.out) {
    }
  }
}
