// Test field accessor inside method accessor argument list.
// .result=COMPILE_PASS
public class Test {
  private Object u;

  class A {
    void f() {
      B.x(u); // Field accessor inside method accessor.
    }
  }

  static class B {
    static private void x(Object u) {
    }
  }
}

class O {
  static Object f(Object u) {
    return u;
  }
}
