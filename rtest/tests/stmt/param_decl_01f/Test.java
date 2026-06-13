// A parameter name may not be identical to another formal parameter name.
// .result=COMPILE_FAIL
class Test {
  void m(int a, float b, String a) {  // Error: duplicate parameter.
  }
}
