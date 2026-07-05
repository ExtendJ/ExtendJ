// .result: COMPILE_PASS
public abstract class Test {
  S<String> n = f(g(h()));

  abstract <U> U f(S<U> a);
  abstract <T> S<T> g(S<S<T>> a);
  abstract <Q> S<S<S<Q>>> h();
}

interface S<E> { }
