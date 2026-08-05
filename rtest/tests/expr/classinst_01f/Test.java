// Unknown typename in class instance expression.
// See issue 194.
// .result=COMPILE_FAIL
public class Test {
  static void f() {
    Test bort = new Test();
    new bort.Test();  // Error bort.Test is not a typename.
  }
}
