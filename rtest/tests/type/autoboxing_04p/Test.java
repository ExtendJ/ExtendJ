// Test autoboxing.
// .result=COMPILE_PASS

class Test {
  int i = 1234;
  Object o = (Object) i;
}
