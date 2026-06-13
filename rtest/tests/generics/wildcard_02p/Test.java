// Test wildcard type substitution and assignment compatibility.
// This test checks that the return type of i.get(), being ? super Number,
// is assignment compatible with java.lang.Object.
// .result=COMPILE_PASS
public class Test {
  interface I<T> {
    T get();
  }

  void test(I<? super Number> i) {
    Object o = i.get();
  }
}
