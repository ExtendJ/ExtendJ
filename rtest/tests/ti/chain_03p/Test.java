// Variant of chain_01p using a lambda expression and with a generic function
// instead of diamond expression.
// .result: COMPILE_PASS
abstract class Test {
  DataStream<Value> input =
      addSource(decorator(new Source<String>()))
          .flatMap((String s) -> new Value());

  abstract <S> Source<S> decorator(Source<S> source);

  abstract <O> DataStream<O> addSource(Source<O> fun);
}

class Value { }

class Source<T> { }

interface DataStream<I> {
  <R> DataStream<R> flatMap(FlatMapper<I,R> fun);
}

// U -> V
interface FlatMapper<U, V> {
  V flatMap(U value);
}
