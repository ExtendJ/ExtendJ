// Test conditional expression type analysis.
// .result=COMPILE_PASS
class Test {
  void test(boolean a, float b, int c) {
    f(a ? b : null); // Calls Test.f(float).
    f(a ? null : c); // Calls Test.f(int).
  }

  void f(float f) {
  }

  void f(int i) {
  }
}
