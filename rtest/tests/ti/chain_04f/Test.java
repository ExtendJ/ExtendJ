// Based on Apache Flink.
// .result: COMPILE_FAIL
abstract class Test {
  DataStream<Value> input =
      addSource(empty())
          .flatMap((String s) -> new Value());

  abstract <U> Source<U> empty();

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
