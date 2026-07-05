// Existential subtype in nested type inference.
// .result: COMPILE_PASS
public abstract class Test {
  S<A> n = f(g());

  abstract <R> S<R> f(S<? extends R> a);
  abstract <T> S<T> g();
}

class A { }
class B extends A { }

interface S<E> { }
