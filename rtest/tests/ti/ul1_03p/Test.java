// .result: COMPILE_PASS
abstract class Test implements I {
  { f(g(h())); }
}

interface I {
      void f(S<B> a);
  <x> S<x> g(S<? super x> a);
      S<A> h();
}

class A { }
class B extends A { }

interface S<E> { }
