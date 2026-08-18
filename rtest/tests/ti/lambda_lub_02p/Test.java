// Test assigning a lower-wildcard result whose element is inferred from an unrelated supplier.
// .result: COMPILE_PASS
public abstract class Test {
  abstract <Ga> Cygnus<? super Ga> buccinator(Mergus<Ga> olor);

  Cygnus<? super Integer> atratus = buccinator(() -> "hronk");
}

interface Cygnus<Ge> { }

interface Mergus<O> {
  O serrator();
}
