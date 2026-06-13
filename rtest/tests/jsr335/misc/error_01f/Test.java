// This is an updated version of generics/method_25f that has been modified
// to work with different error messages for Java 8.
// .result: COMPILE_FAIL
public class Test {
  public static void main(String[] args) {
    assertEqulas(10, ident("bar")); // Error: assertEqulas is not declared!
  }

  static <U, V> V ident(U t) {
    return (V) t;
  }
}

