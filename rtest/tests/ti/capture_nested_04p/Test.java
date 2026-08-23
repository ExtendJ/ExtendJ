// Test invariant-result inference from a nested lower-wildcard argument with an exact target.
// This test technically should not compile according to JLS SE8 but javac does not seem
// to honor the specification in this case.
// .result: COMPILE_PASS
public abstract class Test {
  abstract <N> Reflex<? super N> reflex(N n);
  abstract <U> Reflex<U> mirage(Reflex<U> box);

  // This below invocatino is technically not valid according to JLS SE8:
  // 1. The reflex invocation creates a capture bound ‹Reflex<b1> → capture(Reflex<? super N>)›.
  // 2. The mirage invocation lifts b1 as an inference variable and the bound b1 = U
  // 2. The compatibility constraint ‹Reflex<U> → Reflex<Object>› is incorporated
  //    leading to the bound U = Object and through transitivity, b1 = Object,
  //    but since b1 is part of a capture bound this should reduce to ‹false›
  Reflex<Object> mirage = mirage(reflex("2y7C8gIpyTI"));
}

interface Reflex<T> { }
