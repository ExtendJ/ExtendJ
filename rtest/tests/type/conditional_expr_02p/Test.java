// Tests type analysis of a conditional expression
// See bug report: https://bitbucket.org/extendj/extendj/issues/157/incorrect-binary-numeric-promotion-in
// .result=COMPILE_PASS
class Test {
  byte test(byte a, Byte b, boolean c) {
    return c ? a : b;
  }
}
