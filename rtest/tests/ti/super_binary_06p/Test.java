// No existential type needed.
// .result: COMPILE_PASS
public abstract class Test {
  S<A> n = f(g());

  abstract <U> U f(P<? super S0<A>, U> a);
  abstract <T> P<S0<T>, S<T>> g();
}

class A { }
class B extends A { }

interface S<E> { }
interface S0<E> extends S<E> { }
interface P<L, R> { }

