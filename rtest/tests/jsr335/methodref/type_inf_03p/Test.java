// Test running a method reference with wildcard parameterized type.
// https://bitbucket.org/extendj/extendj/issues/219/broken-bytecode-for-wildcard-parameterized
// .result: EXEC_PASS
public class Test {
  public static void main(String[] args) {
    accept("x", String::toString);
  }

  static <T> void accept(T v, F<? super T> f) {
    f.accept(v);
  }
}

interface F<T> {
  String accept(T v);
}
