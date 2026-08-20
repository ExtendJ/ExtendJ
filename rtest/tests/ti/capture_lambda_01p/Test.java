// Test typing a lambda parameter bounded by the capture of an upper-wildcard argument.
// .result: COMPILE_PASS
public abstract class Test {
  abstract <T, V> void spirit(Meliora<? extends T> meliora, Absent<T, V> better);

  void spirit(Meliora<? extends String> meliora) {
    // ‹meliora → T› is resolved first due to constraint ordering, giving T <: capture#(? extends String).
    // ‹crown → crown.length()› reduces to ‹crown.length() → Absent<capture#(? extends String), V>›
    // and from there V is inferred to be int.
    spirit(meliora, crown -> crown.length());
  }
}

interface Meliora<M> { }

interface Absent<A, R> {
  R excursion(A a);
}
