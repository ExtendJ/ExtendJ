// Test method invocation type conversion.
// See issue 189.
// .result=COMPILE_PASS

class Thing {}
class Container<T extends Thing> {}

class Test {

  static void foo(Container<? extends Thing> i) { }

  void m(Container<?> i) {
    foo(i);  // Should work fine: the upper bound of T is Thing.
  }
}
