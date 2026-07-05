// .result: COMPILE_PASS
abstract class Test implements I {
  { f(g()); }
}

interface I {
        void  f(S<? super B> a);
  <x>   S<x>  g();
}

class A { }
class B extends A { }

interface S<E> { }
