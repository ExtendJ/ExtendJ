// .result: COMPILE_PASS
public class Test {
  Container<Pair> run(Container<Pair<A, B>> in) {
    return in.collect(toContainer());
  }

  static <T> Collector<T, Container<T>> toContainer() {
    return null;
  }
}

// User types:
interface A { }
interface B { }

// Library types:
interface Pair<L, R> { }

interface Container<T> {
  <R> R collect(Collector<? super T, R> f);
}

interface Collector<T, R> { }

