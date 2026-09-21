// Like capture_nested_13p, with an additional nested invocation. Equality
// aliases from both invocations must be substituted in the fresh upper bound.
// .result: COMPILE_PASS
@SuppressWarnings("unchecked")
public abstract class Test<T> {
  abstract <K> Klass<? extends K> inner(Klass<K> klass);
  abstract <I> Klass<I> identity(Klass<I> klass);
  abstract <X extends Copyable<X>> Serial<X> outer(Klass<X> klass);

  <R extends Copyable> Serial<T> translate(Klass<R> klass) {
    return (Serial<T>) outer(identity(inner(klass)));
  }
}

class Copyable<U> { }
interface Serial<F> { }
interface Klass<T> { }
