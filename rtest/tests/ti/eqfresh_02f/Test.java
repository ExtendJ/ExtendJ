// Self-bounded ivar equal-bounded to nested ivar.
// .result: COMPILE_FAIL
public abstract class Test {
  interface Humdinger<H> {}
  static class Goodway<G> {}

  abstract <O extends Humdinger<O>> O outer(Goodway<O> t);

  abstract <I> Goodway<I> inner();

  {
    // O and I are instantiated to distinct fresh tvars so O = I is false.
    outer(inner());
  }
}
