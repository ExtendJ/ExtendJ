// Test conditional expression type analysis.
// .result=COMPILE_PASS
public class Test {
  short test1(boolean a, Short b) {
    return a ? (1<<15) - 1 : b; // Type: short.
  }
  short test2(boolean a, short b) {
    return a ? b : -0x8000; // Type: short.
  }
}
