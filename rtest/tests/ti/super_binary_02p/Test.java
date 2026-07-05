// Existential supertype in nested type inference.
// Type dependency passes through nested expression.
// .result: COMPILE_PASS
public abstract class Test {
  S<A> p = f(g(), new B());

  abstract <U, V> U f(P<? super V, U> a, V b);
  abstract <T> P<T, S<T>> g();
}

class A { }
class B extends A { }

interface S<E> { }
interface P<L, R> { }
