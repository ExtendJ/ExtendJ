// Test method invocation type conversion.
// See issue 189.
// .result=COMPILE_PASS

class Thing {}
interface I<T extends Thing> {}

class Test {

  static void foo(I<? extends Thing> i) { }

  void m(I<?> i) {
    foo(i);  // Should work fine: the upper bound of T is Thing.
  }
}
