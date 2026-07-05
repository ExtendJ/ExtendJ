// Types of g() and h() are related via P type arguments.
// .result: COMPILE_PASS
public abstract class Test {
  String n = f(g(), h());

  abstract <U, V, W> U f(P<U, V> a, P<V, W> b);
  abstract <T> P<T, S<T>> g();
  abstract <Q> P<S<Q>, Q> h();
}

interface S<E> { }
interface P<L, R> { }
