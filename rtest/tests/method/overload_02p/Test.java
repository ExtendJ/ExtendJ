// Test method overload resolution.
// .result=COMPILE_PASS
public class Test {
  void f(Integer[] i) {
  }

  void f(Number[] i) {
  }

  void test(Integer[] i) {
    f(i);
  }
}
