// Removing the cast from capture_nested_13p gives the outer invocation a
// target type. The unrelated T cannot satisfy the recursive Copyable bound.
// .result: COMPILE_FAIL
public abstract class Test<T> {
  abstract <K> Klass<? extends K> inner(Klass<K> klass);
  abstract <X extends Copyable<X>> Serial<X> outer(Klass<X> klass);

  <R extends Copyable> Serial<T> translate(Klass<R> klass) {
    return outer(inner(klass));
  }
}

class Copyable<U> { }
interface Serial<F> { }
interface Klass<T> { }
