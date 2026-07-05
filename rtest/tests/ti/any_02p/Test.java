// .result: COMPILE_PASS
public abstract class Test {
  { f(g(h())); }

  abstract void f(S<A> a);
  abstract <x> x g(S<x> a);
  abstract <y> S<S<y>> h();
}

class A { }
class B extends A { }

interface S<E> { }
