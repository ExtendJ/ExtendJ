// It is not legal to shadow a local variable in the same scope.
// .result=COMPILE_FAIL
class Test {
  void m() {
    int[] array = { 1, 2, 3 };
    for (int element : array) {
      int element = 3; // Error: duplicate declaration.
    }
  }
}
