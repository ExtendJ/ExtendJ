// Test grounding of lambda expressions.
// .result: COMPILE_PASS
public abstract class Test {
  {
    // The lambda is typed against the ground target type derived from the wildcard bounds
    // with T3 determined by the first argument.
    wild("thetis", s -> s.length());
  }

  abstract <T3, R3> R3 wild(T3 t, Thetis<? super T3, R3> f);
}

interface Thetis<I, O> {
  O adapt(I i);
}
