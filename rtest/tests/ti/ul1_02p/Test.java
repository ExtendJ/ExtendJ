// .result: COMPILE_PASS
abstract class Test implements I {
  { f(g()); }
}

interface I {
  <x>  void  f(S<? super x> a);
       S<A>  g();
}

class A { }
class B extends A { }

interface S<E> { }
