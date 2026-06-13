// Test simple definite assignement case for field used in method.
// .result: COMPILE_PASS
class Test {
  int x;

  int pass() {
    return x;
  }
}
