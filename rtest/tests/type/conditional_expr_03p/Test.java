// Test conditional expression type analysis.
// .result=COMPILE_PASS
public class Test {
  short test1(boolean a, Byte b, short c) {
    return a ? b : c; // Type: short.
  }
  short test2(boolean a, byte b, Short c) {
    return a ? b : c; // Type: short.
  }
}
