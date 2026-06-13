// Test accessing a protected member of a superclass in another package using a super access.
// .result: COMPILE_PASS
import p1.C;

class Test extends C {
  int pass() {
    Test.super.m();
    return super.f;
  }
}
