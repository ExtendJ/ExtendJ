// Existential supertype in nested type inference.
// Type dependency passes through nested expression.
// .result: COMPILE_PASS
public abstract class Test {
  S<A> s = f(i(g()));

  abstract <U> U f(P<? super B, U> a);
  abstract <T> P<T, S<T>> g();
  abstract <Q> Q i(Q i);
}

class A { }
class B extends A { }

interface S<E> { }
interface P<L, R> { }
