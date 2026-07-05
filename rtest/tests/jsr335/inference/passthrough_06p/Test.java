// .result: COMPILE_PASS
public class Test {
  List<A> run(List<B> in) {
    return in.collect(toList());
  }

  static <T> Collector<T, List<T>> toList() {
    return null;
  }
}

// User types:
class A { }
class B extends A { }

// Library types:
interface List<T> {
  <R> R collect(Collector<? super T, R> f);
}

interface Collector<T, R> { }
