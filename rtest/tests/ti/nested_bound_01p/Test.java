// Test bound propagations in nested invocations (without wildcard capture).
// .result: COMPILE_PASS
public abstract class Test {
  abstract <B extends Hyvel> Rakhyvel<B, B> inre(B b);
  abstract <S, Y> S yttre(Rakhyvel<S, Y> v);

  // The bound B <: Hyvel is incorporated to B = S before lifting to the outer inference.
  Hyvel hyvel = yttre(inre(new Osthyvel()));
}

interface Hyvel { }
class Osthyvel implements Hyvel { }
interface Rakhyvel<U, V> { }
