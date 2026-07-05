// Check correct handling of casting context in type inference.
// .result: COMPILE_PASS
abstract class Test {
  @SuppressWarnings("unchecked")
  <O> Box<O> g() {
    // The cast to Box<O> should not introduce an extra constraint on the inferred type of U:
    return (Box<O>) h(wc());
  }

  abstract <U> Box<U> h(Box<U> klass);

  abstract Box<?> wc();
}

interface Box<T> { }
