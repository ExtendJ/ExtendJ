// Test method overload resolution.
// .result=COMPILE_PASS
public class Test {
  void f(int i) {
  }

  void f(long i) {
  }

  void f(float i) {
  }

  void f(double i) {
  }

  void test(int i) {
    f(i);
  }

  void test(long i) {
    f(i);
  }

  void test(float i) {
    f(i);
  }

  void test(double i) {
    f(i);
  }
}
