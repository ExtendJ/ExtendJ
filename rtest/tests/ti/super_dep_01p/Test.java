// Existential supertype in nested type inference.
// Type dependency passes through nested expression.
// .result: COMPILE_PASS
public abstract class Test {
  S<A> n = f(g(), g());

  abstract <U, V> V f(P<? super C, S<U>> a, P<? super U, V> b);
  abstract <T> P<T, S<T>> g();
}

class A { }
class B extends A { }
class C extends B { }

interface S<E> { }
interface P<L, R> { }
