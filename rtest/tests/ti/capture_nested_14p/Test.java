// Test member access on the result of a nested invocation with an upper bounded wildcard result.
// .result: COMPILE_PASS
public abstract class Test {
  abstract <S> Jaedong<? extends S> inner(Jaedong<S> jd);
  abstract <T extends Zerg> T outer(Jaedong<T> jd);
  Jaedong<Zerg> jd;
  {
    // T is instantiated to a capture variable with the upper bound Zerg.
    outer(inner(jd)).rush();
  }
}

interface Jaedong<Cr> { }
interface Zerg { void rush(); }
