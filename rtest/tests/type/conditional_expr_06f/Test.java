// Test conditional expression type analysis.
// .result=COMPILE_FAIL
public class Test {
  short test(boolean a, short b) {
    return a ? (1<<16) : b; // Type: int (2^16 does not fit in short).
  }
}
