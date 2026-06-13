// Unknown typename in class instance expression.
// https://bitbucket.org/extendj/extendj/issues/194/unknown-typename-in-class-instance
// .result=COMPILE_FAIL
public class Test {
  static void f() {
    Test bort = new Test();
    new bort.Test();  // Error bort.Test is not a typename.
  }
}
