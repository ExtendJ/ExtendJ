// Like capture_lambda_01p except with a lower bounded wildcard instead of upper bounded.
// .result: COMPILE_PASS
public abstract class Test {
  abstract <T, V> void spirit(Meliora<? extends T> meliora, Absent<T, V> better);

  void spirit(Meliora<? super String> meliora) {
    spirit(meliora, crown -> crown.hashCode());
  }
}

interface Meliora<M> { }

interface Absent<A, R> {
  R excursion(A a);
}
