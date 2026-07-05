// Existential subtype in type inference.
// .result: COMPILE_PASS
public abstract class Test {
  S<A> n = f(g());

  abstract <R> S<R> f(S<? extends R> a);
  abstract S<B> g();
}

class A { }
class B extends A { }

interface S<E> { }
