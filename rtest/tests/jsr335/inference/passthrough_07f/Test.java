// .result: COMPILE_FAIL
public class Test {
  List<A> run(List<B> in) {
    return in.collect(toList()).copy(); // Error: List<B> not assignable to List<A>.
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
  List<T> copy();
}

interface Collector<T, R> { }
