// Minimized standalone test extracted from Apache flink.
// .result: COMPILE_PASS
public class Test<T> {
  Klass<T> type;

  <R extends Copyable> Serial<T> translate(Klass<R> other) {
    return (Serial<T>) createCopyable(type.asSubklass(other));
  }

  static <X extends Copyable<X>> Serial<X> createCopyable(Klass<X> clazz) { return null; }
}

class Copyable<U> { }
interface Serial<F> { }

abstract class Klass<T> {
  abstract <K> Klass<? extends K> asSubklass(Klass<K> klass);
}
