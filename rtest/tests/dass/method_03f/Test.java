// Test definite assignment of host class variable used in anonymous class method.
// .result: COMPILE_PASS
class Test {
  void m() {
    final int x;
    x = 3;
    new Object() {
      int fail() {
        return x; // Error: x is not definitely assigned here.
      }
    };
  }
}
