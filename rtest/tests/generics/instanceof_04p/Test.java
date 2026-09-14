// Test instanceof with a bounded wildcard parameterized interface type.
// .result: COMPILE_PASS
public class Test {
  boolean test(Object o) {
    // TwosComplement<? extends Object> is reifiable.
    return o instanceof TwosComplement<? extends Object>;
  }
}

interface TwosComplement<Tc> { }
