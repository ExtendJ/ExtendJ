// .result: COMPILE_PASS
abstract class Test implements I {
  { f(g(h(i()))); }
}

interface I {
      void f(S<A> a);
  <x> S<x> g(P<? extends A, x> a);
  <y1, y2> P<y1, y2> h(P<? super y1, y2> a);
  <z> P<z, z> i();
}

class A { }
class B extends A { }

interface S<E> { }
interface P<U, V> { }
