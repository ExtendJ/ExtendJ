// Self-bounded ivar equal-bounded to nested ivar.
// Like eqfresh_02f but with a diamond as the nested expression.
// .result: COMPILE_FAIL
public abstract class Test {
  interface Humdinger<H> {}
  static class Goodway<G> {}

  abstract <O extends Humdinger<? super O>> void outer(Goodway<O> n);

  {
    // O and G are instantiated to distinct fresh tvars so O = G is false.
    outer(new Goodway<>());
  }
}
