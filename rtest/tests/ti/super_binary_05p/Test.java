// Existential supertype in nested type inference.
// .result: COMPILE_PASS
public abstract class Test {
  S<B> n = f(g());

  abstract <U, V> S<U> f(P<V, ? super U> a);
  abstract <T> P<T, T> g();
}

class A { }
class B extends A { }

interface S<E> { }
interface P<L, R> { }

