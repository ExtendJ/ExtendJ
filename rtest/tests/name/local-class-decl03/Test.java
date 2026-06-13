// Tests conflicting local class declarations (using same class name twice).
// .result=COMPILE_FAIL
public class Test {
  void m() {
    class A {
      void a_m() {
        class A {};
      }
    };
  }
}
