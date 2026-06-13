// Test conditional expression type analysis.
// .result=COMPILE_PASS
public class Test {
  byte test1(boolean a, byte b) {
    return a ? 127 : b; // Type: byte (127 fits in byte).
  }
  byte test2(boolean a, Byte b) {
    return a ? b : -128; // Type: byte (-128 fits in byte).
  }
}
