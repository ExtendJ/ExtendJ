// Test that a sensible error message is reported for an unbound method access
// when an argument uses type inference.
// https://bitbucket.org/extendj/extendj/issues/251/crash-when-compiling-antlr-in-the
// .result: COMPILE_FAIL
public class Test {
  public static void main(String[] args) {
    assertEqulas(10, ident("bar")); // Error: assertEqulas is not declared!
  }

  static <U, V> V ident(U t) {
    return (V) t;
  }
}

