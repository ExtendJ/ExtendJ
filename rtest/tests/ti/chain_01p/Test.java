// Based on Apache Flink.
// .result: COMPILE_PASS
abstract class Test {
  DataStream<Value> input =
      addSource(new Decorator<>(new Source<String>()))
          .flatMap(new Fun());

  abstract <O> DataStream<O> addSource(Source<O> fun);
}

class Value { }

class Source<X> { }

class Decorator<T> extends Source<T> {
  public Decorator(Source<T> source) {
  }
}

interface DataStream<I> {
  <O> DataStream<O> flatMap(FlatMapper<I,O> fun);
}

// U -> V
interface FlatMapper<U, V> {
  V flatMap(U value);
}

class Fun implements FlatMapper<String, Value> {
  public Value flatMap(String value) {
    return null;
  }
}
