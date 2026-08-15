// Test constraint ordering in inference.
// .result: COMPILE_PASS
public abstract class Test {
  // T must be resolved from the assignment target type before the lambda constraints can be reduced.
  LambDah<String, Integer> mixed = mix(s -> s.length(), s2 -> s2.hashCode());

  abstract <T> T mix(T a, T b);
}

interface LambDah<I, O> {
  O baah(I i);
}
