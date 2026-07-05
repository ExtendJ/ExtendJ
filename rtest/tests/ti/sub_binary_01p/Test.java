// Existential subtype in nested type inference.
// .result: COMPILE_PASS
public abstract class Test {
  S<B> p = f(g());

  abstract <U> U f(P<? extends A, U> a);
  abstract <T> P<T, S<T>> g();
}

class A { }
class B extends A { }

interface S<E> { }
interface P<L, R> { }
