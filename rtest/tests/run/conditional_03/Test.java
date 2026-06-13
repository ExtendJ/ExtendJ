// Test runtime autoboxing forced by null in a conditional expression.
// https://bitbucket.org/extendj/extendj/issues/226/missing-autoboxing-in-generated-code-for
class Test {
  public static void main(String[] args) {
    new Test().test1(true, 0.16, 2L);
    new Test().test2(false, 0.42, 42L);
  }

  void test1(boolean a, double b, long c) {
    f(a ? b : null); // Calls Test.f(double).
  }

  void test2(boolean a, double b, long c) {
    f(a ? null : c); // Calls Test.f(long).
  }

  void f(double v) {
    System.out.println("double: " + v);
  }

  void f(long v) {
    System.out.println("long: " + v);
  }
}
