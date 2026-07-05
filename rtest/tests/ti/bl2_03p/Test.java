// .result: COMPILE_PASS
abstract class Test implements I {
  { f(g(h())); }
}

interface I {
          void          f(S<A> a);
  <x>     S<x>          g(Q<? super B, ? super B, x, x> a);
  <y, z>  Q<y, z, y, z> h();
}

class A { }
class B extends A { }

interface S<E> { }
interface P<L, R> { }
interface Q<T1, T2, T3, T4> { }

