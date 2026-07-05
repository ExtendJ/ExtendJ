// Check correct handling of casting context in type inference.
// .result: COMPILE_PASS
abstract class Test {
  @SuppressWarnings("unchecked")
  // The cast to Box<String> should not introduce an extra constraint on the inferred type of U:
  Box<String> box = (Box<String>) h(wc());

  abstract <U> Box<U> h(Box<U> klass);

  abstract Box<?> wc();
}

interface Box<T> { }
