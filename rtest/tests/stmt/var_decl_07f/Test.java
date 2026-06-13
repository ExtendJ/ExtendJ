// It is not legal to shadow a local variable in the same scope.
// .result=COMPILE_FAIL
class Test {
  void m() {
    int i = 0;
    for (int i = 0; i < -1; ) {  // Error: duplicate declaration.
    }
  }
}
