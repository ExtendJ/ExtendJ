// .result: COMPILE_PASS
public abstract class Test {
  S<A> n = g();

  abstract <T> S<T> g();
}

class A { }
class B extends A { }

interface S<E> { }
