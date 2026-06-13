// Test conditional expression type analysis.
// .result=COMPILE_PASS
class Test {
  void test(boolean a) {
    f(a ? null : null);
  }

  void f(float x) {
  }

  void f(int x) {
  }

  void f(Object x) {
  }
}
