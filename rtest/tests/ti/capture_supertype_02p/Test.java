// The upper bound of a capture variable is the declared bound of the type
// parameter of the type being captured (JLS SE8 §5.1.10). Here the wildcard must
// be captured with the bound of Sub.E (Number) so that the inferred U
// satisfies its own bound. Capturing the supertype Base<?> instead uses the
// bound of Base.T (Object) and the invocation is rejected.
// .result: COMPILE_PASS
abstract class Test {
  abstract <U extends Number> void h(Base<U> b);

  void m(Sub<?> s) {
    h(s); // U is inferred as the capture of Sub's wildcard, bounded by Number.
  }
}

class Base<T> { }

class Sub<E extends Number> extends Base<E> { }
