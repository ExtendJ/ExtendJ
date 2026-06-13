// Test a regression in the code generation causing null pointer exception.
// .result=COMPILE_PASS
enum E {
  C1();

  E() {
  }
}

class Test {
  void m() {
    Object o = E.C1;
  }
}
