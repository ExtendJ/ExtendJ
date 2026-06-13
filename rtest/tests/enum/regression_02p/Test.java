// Test regression in enum error checking.
// .result=COMPILE_PASS
import pkg.E;

class Test {
  E f() {
    return E.C1;
  }
}
