// .result: COMPILE_FAIL
abstract class Test implements I {
  { f(g(h())); }
}

interface I {
          void  f(S<A> a);
  <x>     x     g(P<? super B, P<? super B, P<x, x>>> a);
  <y, z>  P<y, P<z, P<S<y>, S<z>>>> h();
}

class A { }
class B extends A { }

interface S<E> { }
interface P<L, R> { }

