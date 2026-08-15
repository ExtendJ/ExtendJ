// Pertinency of constructor references follows exactness.
// .result: COMPILE_FAIL
public abstract class Test {
  {
    // Djoser::new is exact, so its result constraint makes the generic candidate inapplicable.
    arthur(Djoser::new);
    // Sneferu::new is inexact, so the generic candidate is applicable and the invocation fails instead.
    arthur(Sneferu::new);
  }

  abstract <U> String arthur(Khufu<U, String> f);
  abstract String arthur(Object o);
}

interface Khufu<I, O> {
  O canute(I i);
}

class Djoser {
  Djoser(Integer val) { }
}

class Sneferu<V> {
  Sneferu(Integer val) { }
}
