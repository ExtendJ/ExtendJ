// Variant of eqfresh_02f using multiple inner ivars.
// .result: COMPILE_FAIL
public abstract class Test {
  interface Humdinger<H> {}
  static class Goodway<G1, G2> {}

  abstract <O extends Humdinger<O>> O outer(Goodway<O, O> t);

  abstract <I1, I2> Goodway<I1, I2> inner();

  {
    outer(inner());
  }
}
