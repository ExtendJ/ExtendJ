// A wildcard argument that reaches the formal parameter type through a supertype
// must capture to the same variable during inference and during the applicability
// check (JLS SE8 §5.1.10). Capture conversion is applied to the type of the
// argument expression, not to its parameterized supertype. Capturing the
// supertype instead captures a substituted wildcard which yields a different
// capture variable than the one the applicability check derives from Sub<?>.
// .result: COMPILE_PASS
abstract class Test {
  abstract <U> void h(Base<U> b);

  void m(Sub<?> s) {
    h(s); // U is inferred as the capture of Sub's wildcard.
  }
}

class Base<T> { }

class Sub<E> extends Base<E> { }
