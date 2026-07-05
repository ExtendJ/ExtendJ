// Existential supertype in nested type inference.
// Type dependency passes through nested expression.
// .result: COMPILE_FAIL
public abstract class Test {
  S<A> p = f(g()).copy();

  abstract <U> U f(P<? super B, U> a);
  abstract <T> P<T, S<T>> g();
}

class A { }
class B extends A { }

interface S<E> {
  S<E> copy();
}
interface P<L, R> { }
