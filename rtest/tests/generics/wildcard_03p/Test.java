// Test wildcard type substitution and assignment compatibility.
// This test checks that the type ? extends Number is assignement compatible
// with java.lang.Object.
// .result=COMPILE_PASS
public class Test {
  interface I<T> {
    T get();
  }

  void test(I<? extends Number> i) {
    Object o = i.get();
  }
}
