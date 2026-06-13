// Test generics subtyping compatibility for method call.
// .result=COMPILE_PASS
public class Test {
  interface C<T> {
    <R> void x(I<T, R> f);
  }

  interface I<O, N> {
  }

  static class Impl implements I<String, Integer> {
  }

  void test(C<String> c, Impl i) {
    c.x(i);
  }
}

