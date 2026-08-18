// Test a lambda whose functional interface type argument is inferred to a lub intersection.
// .result: COMPILE_PASS
public abstract class Test {
  abstract <N> N albifrons(Branta<N> fabalis, N indicus);

  Object brachyrhynchus = albifrons(() -> "hoonk", Integer.valueOf(1));
}

interface Branta<F> {
  F leucopsis();
}
