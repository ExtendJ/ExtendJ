// Test handling of an existential super type.
// .result: COMPILE_PASS
public abstract class Test {
  S<B> n = f(g(new A()));

  abstract <R> S<R> f(S<? super R> a);
  abstract <T> S<T> g(T s);
}

class A { }
class B extends A { }

interface S<E> { }
