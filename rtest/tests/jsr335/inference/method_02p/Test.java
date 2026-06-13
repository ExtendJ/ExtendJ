// Tricky type inference scenario which ExtendJ does not handle correctly.
// This is a self-contained version of jsr335/inference/method_01p.
// https://bitbucket.org/extendj/extendj/issues/182/wrong-target-type-for-inferred-method
// .result=COMPILE_PASS

interface List<T> {
}

interface Stream<T> {
  <R, A> R collect(Collector<? super T, A, R> c);
}

interface Collector<T, R, A> {
}

interface CollectorBuilder {
  <T> Collector<T, ?, List<T>> toList();
}

public class Test {
  List<Integer> toList(Stream<Integer> stream, CollectorBuilder builder) {
    // ExtendJ computes the wrong type for 'CollectorBuilder.toList()',
    // leading to no matching method being found.
    return stream.collect(builder.toList());
  }
}
