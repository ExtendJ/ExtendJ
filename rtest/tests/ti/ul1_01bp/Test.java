// .result: COMPILE_PASS
abstract class Test implements I {
  S<? super B> n = f();
}

interface I {
  <x> S<x> f();
}

class A { }
class B extends A { }

interface S<E> { }
