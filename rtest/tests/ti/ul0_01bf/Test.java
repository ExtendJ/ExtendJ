// .result: COMPILE_FAIL
abstract class Test implements I {
  S<B> n = f();
}

interface I {
  S<? super B> f();
}

class A { }
class B extends A { }

interface S<E> { }
