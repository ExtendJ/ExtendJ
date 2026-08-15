// Test constraint ordering in inference.
// .result: COMPILE_PASS
public abstract class Test {
  {
    // A lambda whose result expression is itself an implicitly typed lambda.
    mixalot(s -> n -> s.length() + n, 4);
  }

  abstract <B, C> C mixalot(Mixer<String, Mixer<B, C>> f, B b);
}

interface Mixer<I, O> {
  O mix(I i);
}
