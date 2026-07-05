// .result: COMPILE_PASS
public class Test {
  List<Pair> run(Container<Pair<A, B>> in) {
    return in.map(item -> item).collect(toList());
  }

  static <T> Collector<T, List<T>> toList() {
    return null;
  }
}

// User types:
interface A { }
interface B { }

// Library types:
interface Function<T, R> {
  R apply(T x);
}

interface Pair<L, R> { }

interface List<E> { }

interface Container<T> {
  T get();
  <R> Container<R> map(Function<? super T, ? extends R> f);
  <R> R collect(Collector<? super T, R> f);
}

interface Collector<T, R> { }
