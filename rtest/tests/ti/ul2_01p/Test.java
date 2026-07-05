// .result: COMPILE_PASS
abstract class Test implements I {
  { f(g(h())); }
}

interface I {
      void f(S<A> a);
  <x> S<x> g(S<? super x> a);
  <y> S<y> h();
}

class A { }
class B extends A { }

interface S<E> { }
