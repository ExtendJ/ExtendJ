// .result: COMPILE_PASS
public abstract class Test {
  S<A> n = f(g());

  abstract <R> S<R> f(S0<R> a);
  abstract <T> S<T> g();
}

class A { }
class B extends A { }

interface S<E> extends S0<E> { }
interface S0<X> { }
