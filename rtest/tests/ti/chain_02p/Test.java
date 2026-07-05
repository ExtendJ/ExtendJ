// Variant of chain_01p using lambda expression.
// .result: COMPILE_PASS
abstract class Test {
  DataStream<Value> input =
      addSource(new Decorator<>(new Source<String>()))
          .flatMap((String s) -> new Value());

  abstract <O> DataStream<O> addSource(Source<O> fun);
}

class Value { }

class Source<T> { }

class Decorator<T> extends Source<T> {
  public Decorator(Source<T> source) {
  }
}

interface DataStream<I> {
  <R> DataStream<R> flatMap(FlatMapper<I,R> fun);
}

// U -> V
interface FlatMapper<U, V> {
  V flatMap(U value);
}
