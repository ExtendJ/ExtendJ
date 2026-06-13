// Test autoboxing.
// .result=COMPILE_PASS

class Test {
  int f(Integer i) {
    return i; // Autoboxing conversion Integer->int.
  }
}
