// Test constraint ordering in inference with varargs component type being target type.
// .result: COMPILE_PASS
@SuppressWarnings("unchecked")
public abstract class Test {
  {
    // Both trailing lambdas are typed against Mix<B, C> with B determined by the first argument.
    varchain(s -> s.length(), n -> n + 1, n2 -> n2 * 2);
  }

  abstract <B, C> C varchain(Mix<String, B> f, Mix<B, C>... gs);
}

interface Mix<I, O> {
  O mix(I i);
}
