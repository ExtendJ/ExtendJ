// .result: COMPILE_PASS
public abstract class Test {
  { f(g(h(i()))); }

  abstract void f(S<A> a);
  abstract <x> S<x> g(S<x> a);
  abstract <y> S<y> h(P<? super B, y> a);
  abstract <z> P<z, z> i();
}

class A { }
class B extends A { }

interface S<E> { }
interface P<L, R> { }

