// Test conditional expression type analysis.
// .result=COMPILE_PASS
class Test {
  void test(boolean a, Float b, int[] c) {
    f(a ? b : null);
    f(a ? null : c);
  }

  void f(Float x) {
  }

  void f(int[] x) {
  }
}
