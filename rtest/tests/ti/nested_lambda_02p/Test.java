// Test implicitly typed lambda in nested generic invocation.
// .result: COMPILE_PASS
public abstract class Test {
  abstract <A, R> R inre(Stege<R, A> g, A a);
  abstract <S> S yttre(Kvarn<S> u, S s);

  {
    // R has the bound R <: Kvarn<S>, where S is only resolved by the enclosing invocation
    Object o = yttre(inre(r -> r.kvar(), 1404), "F8mZGDFDceU");
  }
}
interface Stege<P, Q> { Q steg(P p); }
interface Kvarn<U> { int kvar(); }
