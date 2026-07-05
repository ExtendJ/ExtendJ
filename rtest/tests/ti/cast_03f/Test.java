// Overconstrained type variable (U can't be both String and ?).
// .result: COMPILE_FAIL
abstract class Test {
  @SuppressWarnings("unchecked")
  Box<String> box = h(wc());

  abstract <U> Box<U> h(Box<U> klass);

  abstract Box<?> wc();
}

interface Box<T> { }
