// An exact method reference is pertinent to applicability when its target
// type is not a type parameter of the candidate method.
// .result: COMPILE_FAIL
public abstract class Test {
  {
    // The result constraint of the reference makes the generic candidate
    // inapplicable, so no overload matches.
    harold(Test::edgar);
  }

  abstract <U> String harold(Thutmose<U, String> f);
  abstract String harold(Object o);
  abstract Integer edgar(Integer x);
}

interface Thutmose<I, O> {
  O duncan(I i);
}
