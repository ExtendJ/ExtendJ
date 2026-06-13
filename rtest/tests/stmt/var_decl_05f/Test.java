// A local variable may not shadow a parameter.
// .result=COMPILE_FAIL
class Test {
  void m(float x) {
    int x = 10;  // Error: duplicate declaration.
  }
}
