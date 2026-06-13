// Test a regression in syntactic classification using circular nta rewrites.
// .result=COMPILE_PASS
class Test {
  void m() {
    System.out.println(); // Bug: failing to classify out as a variable access.
  }
}
