// Exercise the same-signature test for generic methods.
// See issue 66.
// .result: COMPILE_PASS
class Test {
  interface Y { <S> void execute(S s); }
  interface X { <T> void execute(T s); }
  interface Exec extends Y, X { }

  class InncerClass implements Exec {
    // No problem, the methods are compatible
    public <A> void execute(A a) { }
  }
}
