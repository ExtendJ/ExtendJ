// Test bound propagations in nested invocations (without wildcard capture).
// .result: COMPILE_PASS
public abstract class Test {
  abstract <B extends Hyvel> Rakhyvel<B, ?> inre(B b);
  abstract <S, Y> S yttre(Rakhyvel<S, Y> v);

  // The captured type argument of the result is equal to both B and S.
  Hyvel hjul = yttre(inre(new Osthyvel()));
}

interface Hyvel { }
class Osthyvel implements Hyvel { }
interface Rakhyvel<U, V> { }
