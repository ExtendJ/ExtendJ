// Exercise the same-signature test for generic methods.
// See issue 66.
// .result: COMPILE_PASS
class Test {
  interface Y { <S, T> void execute(S s, T t); }
  interface X { <T, S> void execute(T t, S s); }
  interface Exec extends Y, X { }

  class InnerClass implements Exec {
    // No problem, the methods are compatible
    public <A, B> void execute(A a, B b) { }
  }
}
