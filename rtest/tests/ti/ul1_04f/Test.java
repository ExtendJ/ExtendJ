// .result: COMPILE_FAIL
abstract class Test implements I {
  { f(g(h())); }
}

interface I {
      void          f(S<A> a);
  <x> S<x>          g(S<x> a);
      S<? super B>  h();
}

class A { }
class B extends A { }

interface S<E> { }
