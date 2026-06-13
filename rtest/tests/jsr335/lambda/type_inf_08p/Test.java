// Test running a lambda with inferred type.
// This is a simpler version of lambda/type_inf_07p.
// .result: EXEC_PASS
public class Test {
  public static void main(String[] args) {
    accept("x", e->{});
  }

  static <T> void accept(T v, F<? super T> f) {
    f.accept(v);
  }
}

interface F<T> {
  void accept(T v);
}
