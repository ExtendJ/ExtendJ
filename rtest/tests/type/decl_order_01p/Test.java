// Test that declare-before-use error is not generated when declaration and use
// are in the same declarator list.
// .result=COMPILE_PASS
class Test {
  int f1 = 1, f2 = f1 * 3 + 2;

  void m() {
    int v1 = 9, v2 = v1 * 8 + 7;
  }
}
