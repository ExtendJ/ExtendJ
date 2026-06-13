// Calling a parameterized static method always requires qualifier type when
// using explicit type arguments.
// .result=EXEC_PASS
public class Test {
  public static void main(String[] args) {
    Test.<Number>equals(123, Integer.valueOf(123)); // Explicit type arguments.
    boolean result = equals(123, Integer.valueOf(123));
    if (!Test.<Number>equals(321, Integer.valueOf(321))) { // Explicit type arguments.
      throw new Error();
    }
    if (Test.<Number>equals(123, Integer.valueOf(321))) { // Explicit type arguments.
      throw new Error();
    }
    String str1 = (String) foo(equals(123, Integer.valueOf(321)));
    String str2 = (String) Test.foo(Test.<String>listOf("123", "321")); // Explicit type arguments.
    Test.<String>listOf("123", "321"); // Explicit type arguments.
    if (listOf("123", "321").size() != 2) {
      throw new Error();
    }
  }

  public static <T> boolean equals(T a, T b) {
    return a.equals(b);
  }

  public static Object foo(Object o) {
    return "" + o;
  }

  @SuppressWarnings("unchecked")
  public static <T> java.util.List<T> listOf(T... elements) {
    java.util.List<T> result = new java.util.LinkedList<T>();
    for (T elem : elements) {
      result.add(elem);
    }
    return result;
  }
}
