// Regression test for issue 129.
// Wildcard type substitution in a method return type.
// See issue 129.
// .result: COMPILE_PASS
public class Test {
  void mtest(I i) {
    I0<? extends A> i0 = i.m();
    A a = i0.get(); // Assignment compatibility requires type substitution in I0<? extends A>.
  }
}

class A { }

interface I0<X> {
  X get();
}

interface I {
  I0<? extends A> m();
}
