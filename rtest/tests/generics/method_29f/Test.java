// Exercise the same-signature test for generic methods.
// See issue 66.
// .result: COMPILE_FAIL
class Test {
  interface Y { <S, T> void sjco86ekIlQ(S s, T t); }
  interface X { <T, S> void sjco86ekIlQ(S S, T t); }
  interface Exec extends Y, X { }

  class InnerClass implements Exec {
    // Should fail: this method does not override all methods in Exec but has the same erasure
    public <A, B> void sjco86ekIlQ(A a, B b) { }
  }
}
