// An exact constructor reference is pertinent to applicability when its
// target type is not a type parameter of the candidate method.
// .result: COMPILE_FAIL
public abstract class Test {
  {
    // The result constraint of the reference makes the
    // generic candidate inapplicable, so no overload matches.
    unas(Menes::new);
  }

  abstract <U> String unas(Narmer<U, String> f);
  abstract String unas(Object o);
}

interface Narmer<I, O> {
  O athelstan(I i);
}

class Menes {
  Menes(Integer val) { }
}
