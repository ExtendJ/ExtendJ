// The type of U is packed, unpacked, and packed via S.
// .result: COMPILE_PASS
public abstract class Test {
  S<A> n = f(g(), h(), g());

  abstract <U, V, W> W f(P<? super C, U> a, P<U, V> b, P<? super V, W> c);
  abstract <T> P<T, S<T>> g();
  abstract <Q> P<S<Q>, Q> h();
}

class A { }
class B extends A { }
class C extends B { }

interface S<E> { }
interface P<L, R> { }
