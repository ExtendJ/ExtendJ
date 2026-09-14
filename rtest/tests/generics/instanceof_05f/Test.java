// Test instanceof with a bounded wildcard parameterized interface type.
// .result: COMPILE_FAIL
public class Test {
  boolean test(Object o) {
    // TwosComplement<? extends Integer> is not reifiable.
    return o instanceof TwosComplement<? extends Integer>;
  }
}

interface TwosComplement<Tc> { }
