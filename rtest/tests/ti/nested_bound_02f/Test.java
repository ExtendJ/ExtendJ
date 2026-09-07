// Test bound propagations in nested invocations (without wildcard capture).
// .result: COMPILE_FAIL
public abstract class Test<T> {
  abstract <B extends Hyvel> Rakhyvel<B, B> inre(B b);
  abstract <S, Y> S yttre(Rakhyvel<S, Y> v);

  Object hjul(T t) {
    // T is not an inference variable, so the bound B <: Hyvel is checked.
    return yttre(inre(t));
  }
}

interface Hyvel { }
interface Rakhyvel<U, V> { }
