// .result: COMPILE_PASS
public abstract class Test {
  S<A> n = f(g());

  abstract <R> S<R> f(S0<S1<R>> a);
  abstract <T> S<T> g();
}

class A { }
class B extends A { }

interface S<E> extends S0<S1<E>> { }
interface S0<X> { }
interface S1<Y> { }
