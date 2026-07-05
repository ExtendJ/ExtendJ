// .result: COMPILE_FAIL
abstract class Test implements I {
  { f(g()); }
}

interface I {
  void         f(S<B> a);
  S<? super B> g();
}

class A { }
class B extends A { }

interface S<E> { }
