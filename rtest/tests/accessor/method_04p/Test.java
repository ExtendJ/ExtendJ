// Test error in ExtendJ bytecode generation.
// See https://bitbucket.org/extendj/extendj/issues/152/method-lookup-fails-inside-accessor-nta
// .result=COMPILE_PASS
public class Test {

  static class A {
    void f() {
      Object u = new Object();
      B.x(O.f(u)); // Method lookup inside method call requiring accessor.
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
