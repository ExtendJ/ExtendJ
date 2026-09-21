// Test lifted capture variable incorporating self-bounds from outer method.
// Variant of capture_nested_13p.
// .result: COMPILE_PASS
@SuppressWarnings("unchecked")
public abstract class Test<T> {
  abstract <K> Klass<? extends K> inner(Klass<K> klass);
  abstract <X extends Copyable<X>> Serial<X> outer(Klass<X> klass);

  <R extends Copyable<R>> Serial<T> translate(Klass<R> klass) {
    return (Serial<T>) outer(inner(klass));
  }
}

class Copyable<U> { }
interface Serial<F> { }
interface Klass<T> { }
