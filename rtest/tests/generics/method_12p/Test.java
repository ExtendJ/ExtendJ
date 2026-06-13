// Test method invocation type conversion.
// https://bitbucket.org/extendj/extendj/issues/189/wildcard-type-argument-is-not-compatible
// .result=COMPILE_PASS

class Thing {}
class Container<T extends Thing> {}

class Test {

  static void foo(Container<? extends Thing> i) { }

  void m(Container<?> i) {
    foo(i);  // Should work fine: the upper bound of T is Thing.
  }
}
