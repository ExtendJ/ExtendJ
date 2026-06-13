// It is not legal to shadow a local variable in the same scope.
// https://bitbucket.org/extendj/extendj/issues/179/local-variables-can-be-declared-multiple
// .result=COMPILE_FAIL
class Test {
  void m() {
    double x = 10.0;
    int x = 10;  // Error: duplicate declaration.
  }
}
