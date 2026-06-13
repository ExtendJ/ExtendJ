// It is not legal to shadow a local variable in the same scope.
// .result=COMPILE_FAIL
class Test {
  void m() {
    int x, x;  // Error: duplicate declaration.
  }
}
