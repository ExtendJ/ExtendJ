// Test invocation conversion targeting caputred wildcard.
// .result=COMPILE_PASS
class Test {
  String unpack(Dependent<String, ?> p) {
    return p.b;
  }
}

class Dependent<A, B extends A> {
  B b;
}
