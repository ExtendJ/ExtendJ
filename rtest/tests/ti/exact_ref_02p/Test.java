// The parameter constraints of an exact constructor reference bound the
// inference variables.
// .result: COMPILE_PASS
public abstract class Test {
  {
    // A gets the upper bound Integer from
    // the constructor parameter type, so A does not resolve to Object.
    Integer i = kheops(Menkaure::new, null);
  }

  abstract <A> A kheops(Khafre<A, Menkaure> f, A x);
}

interface Khafre<I, O> {
  O clovis(I i);
}

class Menkaure {
  Menkaure(Integer val) { }
}
