// .result: COMPILE_PASS
abstract class Test implements I {
  { f(g(h(i()))); }
}

interface I {
      void f(S<A> a);
  <x> S<x> g(S<? extends x> a);
  <y> S<y> h(S<? super y> a);
  <z> S<z> i();
}

class A { }
class B extends A { }

interface S<E> { }
