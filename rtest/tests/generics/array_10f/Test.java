// Test array element type reifiability.
// .result: COMPILE_FAIL
public class Test {
  // error: TwosComplement<? extends Integer> is not reifiable.
  { new TwosComplement<? extends Integer>[62]; }
}

interface TwosComplement<Tc> { }
