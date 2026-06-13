// It is not legal to shadow a local variable in the same scope.
// .result=COMPILE_FAIL
class Test {
  void m() {
    for (float x = 0; x < 10; x += 1.5f) {
      int x = 10;  // Error: duplicate declaration.
    }
  }
}
