// .result: COMPILE_PASS
abstract class Test implements I {
  { f(g(h())); }
}

interface I {
      void        f(S<A> a);
  <x> x           g(P<? super B, x> a);
  <y> P<y, S<y>>  h();
}

class A { }
class B extends A { }

interface S<E> { }
interface P<L, R> { }

