// Test method invocation type conversion.
// https://bitbucket.org/extendj/extendj/issues/189/wildcard-type-argument-is-not-compatible
// .result=COMPILE_PASS

class Thing {}
interface I<T extends Thing> {}

class Test {

  static void foo(I<? extends Thing> i) { }

  void m(I<?> i) {
    foo(i);  // Should work fine: the upper bound of T is Thing.
  }
}
