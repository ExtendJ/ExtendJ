// .result: COMPILE_FAIL
abstract class Test implements I {
  S<A> n = g(h());
}

interface I {
  <x> S<x>          g(S<x> a);
      S<? super B>  h();
}

class A { }
class B extends A { }

interface S<E> { }
