// A fixed-arity varargs invocation where the argument type can only be
// inferred from the target type. The inferred type of make() is String[],
// which is passed directly as the varargs array. Wrapping the argument as
// a single element inserts a bogus cast of String[] to String.
// .result=EXEC_PASS
public class Test {
  static void m(String... a) {
    System.out.println(a[0]);
  }

  @SuppressWarnings("unchecked")
  static <T> T[] make() {
    return (T[]) new String[] { "wixujwtiLdM" };
  }

  public static void main(String[] args) {
    m(make());
  }
}
