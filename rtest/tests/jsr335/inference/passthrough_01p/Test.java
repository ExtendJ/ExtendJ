// .result: COMPILE_PASS
public class Test {
  Container<Pair> run(Container<Pair<A, B>> in) {
    return in.map(item -> item);
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

interface Container<T> {
  T get();
  <R> Container<R> map(Function<? super T, ? extends R> f);
}
