// Test a regression in syntactic classification that caused p1.A to be
// classified as a package name.
// .result=COMPILE_PASS
class Test {
  p1.A.X x;
}
