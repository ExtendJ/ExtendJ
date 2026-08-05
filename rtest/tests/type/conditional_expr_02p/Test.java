// Tests type analysis of a conditional expression
// See issue 157.
// .result=COMPILE_PASS
class Test {
  byte test(byte a, Byte b, boolean c) {
    return c ? a : b;
  }
}
