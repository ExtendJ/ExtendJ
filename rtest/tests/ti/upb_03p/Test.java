// .result: COMPILE_PASS
public abstract class Test {
  { f(g(h(i()))); }

  abstract void f(S<A> a);
  abstract <x> x g(x a);
  abstract <y> y h(P<? super B, y> a);
  abstract <z> P<z, S<z>> i();
}

class A { }
class B extends A { }

interface S<E> { }
interface P<L, R> { }

