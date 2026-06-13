// Test definite unassignment of host class variable used in anonymous class method.
// .result: COMPILE_FAIL
class Test {
  void m() {
    final int x;
    new Object() {
      int fail() {
        return x; // Error: x is not definitely assigned here.
      }
    };
    x = 3;
  }
}
